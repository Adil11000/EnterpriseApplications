package rajouai.adil.reservationplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
}
