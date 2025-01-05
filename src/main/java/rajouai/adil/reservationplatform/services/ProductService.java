package rajouai.adil.reservationplatform.services;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.entities.Product;
import rajouai.adil.reservationplatform.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
