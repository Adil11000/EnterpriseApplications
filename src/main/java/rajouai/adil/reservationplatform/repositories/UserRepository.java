package rajouai.adil.reservationplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}