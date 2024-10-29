package tn.esprit.tpfoyer;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class EtudiantRestControllerTest {

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    @Mock
    private IEtudiantService etudiantService;

    @Autowired
    private MockMvc mockMvc;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiant = new Etudiant(1L, "John", "Doe", 12345678L, null, null);
    }

    @Test
    @Order(1)
    void testGetEtudiants() throws Exception {
        when(etudiantService.retrieveAllEtudiants()).thenReturn(Arrays.asList(etudiant));

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomEtudiant").value("John"));
        verify(etudiantService, times(1)).retrieveAllEtudiants();
    }

    @Test
    @Order(2)
    void testRetrieveEtudiant() throws Exception {
        when(etudiantService.retrieveEtudiant(1L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));
        verify(etudiantService, times(1)).retrieveEtudiant(1L);
    }

    @Test
    @Order(3)
    void testAddEtudiant() throws Exception {
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));
        verify(etudiantService, times(1)).addEtudiant(any(Etudiant.class));
    }

    @Test
    @Order(4)
    void testModifyEtudiant() throws Exception {
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));
        verify(etudiantService, times(1)).modifyEtudiant(any(Etudiant.class));
    }

    @Test
    @Order(5)
    void testRemoveEtudiant() throws Exception {
        doNothing().when(etudiantService).removeEtudiant(1L);

        mockMvc.perform(delete("/etudiant/remove-etudiant/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(etudiantService, times(1)).removeEtudiant(1L);
    }

    @Test
    @Order(6)
    void testRetrieveEtudiantNotFound() throws Exception {
        when(etudiantService.retrieveEtudiant(999L)).thenThrow(new RuntimeException("Etudiant not found"));

        mockMvc.perform(get("/etudiant/retrieve-etudiant/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void testAddEtudiantInvalid() throws Exception {
        // Testing with invalid data, such as null values
        Etudiant invalidEtudiant = new Etudiant(); // Empty Etudiant

        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidEtudiant)))
                .andExpect(status().isBadRequest());
    }
}
