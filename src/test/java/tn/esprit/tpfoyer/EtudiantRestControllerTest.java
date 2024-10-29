package tn.esprit.tpfoyer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Ensure this is imported
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EtudiantRestControllerTest {

    @Mock
    private IEtudiantService etudiantService;

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantRestController).build();
    }

    @Test
    public void testAddEtudiantInvalidInput() throws Exception {
        // Given an invalid Etudiant object (e.g., missing required fields)
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant(""); // Invalid input

        // When the addEtudiant endpoint is called
        mockMvc.perform(post("/etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiant)))
                .andExpect(status().isBadRequest()); // Expect a 400 Bad Request
    }

    @Test
    public void testModifyEtudiantNotFound() throws Exception {
        // Given an Etudiant ID that does not exist
        long nonExistentId = 999L;
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(nonExistentId);
        etudiant.setNomEtudiant("John");
        etudiant.setPrenomEtudiant("Doe");

        when(etudiantService.findById(nonExistentId)).thenReturn(Optional.empty());

        // When the modifyEtudiant endpoint is called
        mockMvc.perform(put("/etudiant/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiant)))
                .andExpect(status().isNotFound()); // Expect a 404 Not Found
    }

    @Test
    public void testRetrieveEtudiantNotFound() throws Exception {
        // Given an Etudiant ID that does not exist
        long nonExistentId = 999L;

        when(etudiantService.findById(nonExistentId)).thenReturn(Optional.empty());

        // When the retrieveEtudiant endpoint is called
        mockMvc.perform(get("/etudiant/{id}", nonExistentId)) // Ensure the GET request is correct
                .andExpect(status().isNotFound()); // Expect a 404 Not Found
    }

    @Test
    public void testRetrieveEtudiantParCinNotFound() throws Exception {
        // Given a CIN that does not exist
        long invalidCin = 999999;

        when(etudiantService.recupererEtudiantParCin(invalidCin)).thenReturn(null); // Assuming this method returns null when not found

        // When the retrieveEtudiantParCin endpoint is called
        mockMvc.perform(get("/etudiant/cin/{cin}", invalidCin))
                .andExpect(status().isNotFound()); // Expect a 404 Not Found
    }
}
