package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.Product;
import rajouai.adil.reservationplatform.repository.ProductRepository;

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
