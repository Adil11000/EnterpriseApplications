package rajouai.adil.reservationplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long id);
    Optional<Reservation> findByIdAndUserId(Long id, Long userId);
}
