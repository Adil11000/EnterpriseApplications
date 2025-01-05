package rajouai.adil.reservationplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rajouai.adil.reservationplatform.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
}
