package rajouai.adil.reservationplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}