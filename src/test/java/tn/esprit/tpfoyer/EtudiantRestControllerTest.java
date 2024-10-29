package tn.esprit.tpfoyer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EtudiantRestControllerTest {

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    @Mock
    private IEtudiantService etudiantService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantRestController).build();
    }

    @Test
    void testGetEtudiants() throws Exception {
        List<Etudiant> etudiantList = new ArrayList<>();
        etudiantList.add(new Etudiant(1, "John", "Doe", 123456789, null, null));
        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiantList);

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nomEtudiant").value("John"));

        verify(etudiantService, times(1)).retrieveAllEtudiants();
    }

    @Test
    void testGetEtudiantsEmptyList() throws Exception {
        // Arrange
        List<Etudiant> etudiantList = new ArrayList<>();
        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiantList);

        // Act & Assert
        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        verify(etudiantService, times(1)).retrieveAllEtudiants();
    }

    @Test
    void testRetrieveEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456789, null, null);
        when(etudiantService.retrieveEtudiant(1L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomEtudiant").value("John"));

        verify(etudiantService, times(1)).retrieveEtudiant(1L);
    }

    @Test
    void testRetrieveEtudiantNotFound() throws Exception {
        when(etudiantService.retrieveEtudiant(1L)).thenThrow(new RuntimeException("Etudiant not found"));

        mockMvc.perform(get("/etudiant/retrieve-etudiant/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Etudiant not found"));

        verify(etudiantService, times(1)).retrieveEtudiant(1L);
    }

    @Test
    void testAddEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456789, null, null);
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));

        verify(etudiantService, times(1)).addEtudiant(any(Etudiant.class));
    }

    @Test
    void testAddEtudiantInvalidInput() throws Exception {
        // Testing with invalid input
        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomEtudiant\":\"\"}")) // Empty name
                .andExpect(status().isBadRequest());

        verify(etudiantService, times(0)).addEtudiant(any(Etudiant.class));
    }

    @Test
    void testRemoveEtudiant() throws Exception {
        Long etudiantId = 1L;

        mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", etudiantId))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).removeEtudiant(etudiantId);
    }

    @Test
    void testModifyEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456789, null, null);
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));

        verify(etudiantService, times(1)).modifyEtudiant(any(Etudiant.class));
    }

    @Test
    void testModifyEtudiantNotFound() throws Exception {
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenThrow(new RuntimeException("Etudiant not found"));

        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456789, null, null);

        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etudiant)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Etudiant not found"));

        verify(etudiantService, times(1)).modifyEtudiant(any(Etudiant.class));
    }

    @Test
    void testRetrieveEtudiantParCin() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456789, null, null);
        when(etudiantService.recupererEtudiantParCin(123456789)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/{cin}", 123456789))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomEtudiant").value("John"));

        verify(etudiantService, times(1)).recupererEtudiantParCin(123456789);
    }

    @Test
    void testRetrieveEtudiantParCinNotFound() throws Exception {
        when(etudiantService.recupererEtudiantParCin(123456789)).thenThrow(new RuntimeException("Etudiant not found"));

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/{cin}", 123456789))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Etudiant not found"));

        verify(etudiantService, times(1)).recupererEtudiantParCin(123456789);
    }
}
