package rajouai.adil.reservationplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.model.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}