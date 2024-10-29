package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
public class EtudiantRestController {

    private final IEtudiantService etudiantService;

    @GetMapping("/retrieve-all-etudiants")
    public List<Etudiant> getEtudiants() {
        return etudiantService.retrieveAllEtudiants();
    }

    @GetMapping("/retrieve-etudiant-cin/{cin}")
    public Etudiant retrieveEtudiantParCin(@PathVariable("cin") Long cin) {
        return etudiantService.recupererEtudiantParCin(cin);
    }

    @GetMapping("/retrieve-etudiant/{etudiant-id}")
    public Etudiant retrieveEtudiant(@PathVariable("etudiant-id") Long chId) {
        return etudiantService.retrieveEtudiant(chId);
    }

    // http://localhost:8089/tpfoyer/etudiant/add-etudiant
    @PostMapping("/add-etudiant")
    public Etudiant addEtudiant(@RequestBody Etudiant c) {
        return etudiantService.addEtudiant(c);
    }

    @DeleteMapping("/remove-etudiant/{etudiant-id}")
    public void removeEtudiant(@PathVariable("etudiant-id") Long chId) {
        etudiantService.removeEtudiant(chId);
    }

    // http://localhost:8089/tpfoyer/etudiant/modify-etudiant
    @PutMapping("/modify-etudiant")
    public Etudiant modifyEtudiant(@RequestBody Etudiant c) {
        return etudiantService.modifyEtudiant(c);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> modifyEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiantDetails) {
        Etudiant etudiant = etudiantService.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant not found"));
        // Update the details
        etudiant.setNomEtudiant(etudiantDetails.getNomEtudiant());
        etudiantService.save(etudiant);
        return ResponseEntity.ok(etudiant);
    }
}
