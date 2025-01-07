package rajouai.adil.reservationplatform.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.Category;
import rajouai.adil.reservationplatform.model.Product;
import rajouai.adil.reservationplatform.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listProductUsingExample(Pageable pageable,
                                                 Optional<Category> category,
                                                 String query) {
        return productRepository.findByCategoryAndNameOrDescription(category
                        .map(Category::getId)
                        .orElse(null),
                query, pageable);
    }
}
