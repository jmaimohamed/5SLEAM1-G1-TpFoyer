package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ChambreServiceImpl implements IChambreService {

    ChambreRepository chambreRepository;

    public List<Chambre> retrieveAllChambres() {
        log.info("In Method retrieveAllChambres: ");
        List<Chambre> listC = chambreRepository.findAll();
        log.info("Out of retrieveAllChambres: ");

        return listC;
    }

    public Chambre retrieveChambre(Long chambreId) {
        return chambreRepository.findById(chambreId)
                .orElseThrow(() -> new RuntimeException("Chambre not found with id: " + chambreId));
    }

    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    public Chambre modifyChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    public void removeChambre(Long chambreId) {
        chambreRepository.deleteById(chambreId);
    }




    public void removeAllChambres() {
        chambreRepository.deleteAll();
    }




    public List<Chambre> recupererChambresSelonTyp(TypeChambre tc)
    {
        return chambreRepository.findAllByTypeC(tc);
    }

    @Override
    // Réserver une chambre disponible
    public Chambre reserverChambreDisponible(TypeChambre typeChambre) {
        List<Chambre> chambresDisponibles = chambreRepository.findAllByTypeC(typeChambre)
                .stream()
                .filter(Chambre::isDisponible)  // Filtrer les chambres disponibles
                .collect(Collectors.toList());

        if (!chambresDisponibles.isEmpty()) {
            Chambre chambre = chambresDisponibles.get(0);  // Prendre la première chambre disponible
            chambre.setDisponible(false);  // Marquer comme non disponible
            chambreRepository.save(chambre);  // Sauvegarder l'état de la chambre
            return chambre;
        } else {
            throw new RuntimeException("Aucune chambre disponible de ce type");
        }
    }

    // Libérer une chambre
    @Override
    public Chambre libererChambre(Long numeroChambre) {
        Chambre chambre = chambreRepository.findChambreByNumeroChambre(numeroChambre);

        if (chambre != null) {
            chambre.setDisponible(true);  // Libérer la chambre
            chambreRepository.save(chambre);  // Sauvegarder les modifications
            return chambre;
        } else {
            throw new RuntimeException("Chambre avec le numéro " + numeroChambre + " non trouvée");
        }
    }

    public Chambre trouverchambreSelonEtudiant(long cin) {
        return chambreRepository.trouverChselonEt(cin);
    }
}
