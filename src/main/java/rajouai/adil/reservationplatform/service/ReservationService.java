package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.Reservation;
import rajouai.adil.reservationplatform.model.User;
import rajouai.adil.reservationplatform.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations(User user) {
        return reservationRepository.findByUserId(user.getId());
    }

    public Reservation returnReservation(User user, Long reservationId) {
        Reservation reservation =
                reservationRepository
                        .findByIdAndUserId(reservationId, user.getId())
                        .orElseThrow();
        reservation.setReturned(true);
        return reservationRepository.save(reservation);
    }
}
