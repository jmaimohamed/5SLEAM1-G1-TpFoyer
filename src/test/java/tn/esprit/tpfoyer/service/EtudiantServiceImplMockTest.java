package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation; // Import this if you have a Reservation class
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplMockTest {

    @Mock
    EtudiantRepository etudiantRepository;

    @InjectMocks
    EtudiantServiceImpl etudiantService;

    Etudiant etudiant;

    @BeforeEach
    void setUp() {
        // Initialize a sample Etudiant object before each test
        Set<Reservation> reservations = new HashSet<>(); // Create an empty set for reservations
        etudiant = new Etudiant(1L, "John", "Doe", 123456, new Date(), reservations);
    }

    @Test
    void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(etudiant.getNomEtudiant(), result.get(0).getNomEtudiant());
    }

    @Test
    void testRetrieveEtudiant() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(1L);

        assertNotNull(result);
        assertEquals(etudiant.getNomEtudiant(), result.getNomEtudiant());
    }

    @Test
    void testAddEtudiant() {
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);

        assertNotNull(result);
        assertEquals(etudiant.getNomEtudiant(), result.getNomEtudiant());
    }

    @Test
    void testModifyEtudiant() {
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.modifyEtudiant(etudiant);

        assertNotNull(result);
        assertEquals(etudiant.getNomEtudiant(), result.getNomEtudiant());
    }

    @Test
    void testRemoveEtudiant() {
        assertDoesNotThrow(() -> etudiantService.removeEtudiant(1L));
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRecupererEtudiantParCin() {
        when(etudiantRepository.findEtudiantByCinEtudiant(123456)).thenReturn(etudiant);

        Etudiant result = etudiantService.recupererEtudiantParCin(123456);

        assertNotNull(result);
        assertEquals(etudiant.getCinEtudiant(), result.getCinEtudiant());
    }
}
