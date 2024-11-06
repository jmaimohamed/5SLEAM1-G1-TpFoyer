package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ChambreServiceImplMockitoTest {

    @Mock
    ChambreRepository chambreRepository;

    @InjectMocks
    ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLibererChambre() {
        Long numeroChambre = 101L;
        Chambre mockChambre = new Chambre();
        mockChambre.setNumeroChambre(numeroChambre);
        mockChambre.setDisponible(false);

        when(chambreRepository.findChambreByNumeroChambre(numeroChambre)).thenReturn(mockChambre);

        Chambre chambreLiberee = chambreService.libererChambre(numeroChambre);

        assertTrue(chambreLiberee.isDisponible(), "La chambre doit être libérée");
        verify(chambreRepository, times(1)).save(chambreLiberee);
    }
}
