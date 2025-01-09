package rajouai.adil.reservationplatform.reservation;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.user.User;

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
        // Is the reservation associated with the id and the user?
        Reservation reservation =
                reservationRepository
                        .findByIdAndUserId(reservationId, user.getId())
                        .orElseThrow();
        // Mark the reservation as returned
        reservation.setReturned(true);
        return reservationRepository.save(reservation);
    }
}
