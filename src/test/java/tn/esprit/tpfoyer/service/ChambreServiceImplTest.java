package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChambreServiceImplTest {

    @Autowired
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        // Nettoyer la base avant chaque test
        chambreService.removeAllChambres();

        // Ajouter des chambres pour les tests
        chambreService.addChambre(new Chambre(101L, TypeChambre.SIMPLE, true));
        chambreService.addChambre(new Chambre(102L, TypeChambre.DOUBLE, true));
        chambreService.addChambre(new Chambre(103L, TypeChambre.TRIPLE, false)); // Non disponible
    }

    @Test
    void testReserverChambreDisponible() {
        TypeChambre type = TypeChambre.SIMPLE;

        // Réserver une chambre disponible
        Chambre chambreReservee = chambreService.reserverChambreDisponible(type);

        // Vérifications
        assertNotNull(chambreReservee, "La chambre réservée ne doit pas être nulle");
        assertEquals(type, chambreReservee.getTypeC(), "Le type de la chambre doit correspondre");
        assertFalse(chambreReservee.isDisponible(), "La chambre doit être marquée comme non disponible après réservation");
    }

    @Test
    void testReserverChambreIndisponible() {
        TypeChambre type = TypeChambre.TRIPLE;  // Type sans chambre disponible

        // Vérification que l'exception est levée
        Exception exception = assertThrows(RuntimeException.class, () -> {
            chambreService.reserverChambreDisponible(type);
        });

        // Vérification du message d'erreur
        assertEquals("Aucune chambre disponible de ce type", exception.getMessage());
    }
}
