package rajouai.adil.reservationplatform.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    Optional<User> findByEmailAndHashedPassword(String email, String hashedPassword);
    @Transactional
    default User updateOrInsert(User user) {
        return save(user);
    }
}