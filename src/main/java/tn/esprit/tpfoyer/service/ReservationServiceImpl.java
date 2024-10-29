package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation retrieveReservation(String reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            return optionalReservation.get(); // Safely retrieve the value
        } else {
            // Handle the case where the reservation is not found
            throw new RuntimeException("Reservation not found with id: " + reservationId);
        }
    }

    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    public Reservation modifyReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> trouverResSelonDateEtStatus(Date d, boolean b) {
        return reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(d, b);
    }

    public void removeReservation(String reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
