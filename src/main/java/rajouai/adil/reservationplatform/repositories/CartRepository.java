package rajouai.adil.reservationplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.entities.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}
