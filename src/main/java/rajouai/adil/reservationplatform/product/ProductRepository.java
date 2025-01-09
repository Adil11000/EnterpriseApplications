package rajouai.adil.reservationplatform.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Query By Example was not enough to implement the search functionality
    // https://stackoverflow.com/questions/42584191/spring-query-by-example-using-or/45303869#45303869
    @Query("SELECT p FROM Product p " +
            "WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:searchTerm IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "     OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Product> findByCategoryAndNameOrDescription(
            @Param("categoryId") Long categoryId,
            @Param("searchTerm") String query,
            Pageable pageable
    );
}
