package rajouai.adil.reservationplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rajouai.adil.reservationplatform.model.Category;
import rajouai.adil.reservationplatform.model.SlimCategoryProjection;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.id AS id, c.name AS name FROM Category c")
    List<SlimCategoryProjection> findAllCategoryNames();
}