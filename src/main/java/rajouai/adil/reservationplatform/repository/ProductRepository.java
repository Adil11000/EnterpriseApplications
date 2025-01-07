package rajouai.adil.reservationplatform.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rajouai.adil.reservationplatform.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p " +
            "WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:searchTerm IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "     OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Product> findByCategoryAndNameOrDescription(
            @Param("categoryId") Long categoryId,
            @Param("searchTerm") String query,
            Pageable pageable
    );
}
