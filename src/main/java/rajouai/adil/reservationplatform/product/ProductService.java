package rajouai.adil.reservationplatform.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.category.Category;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> listProductUsingExample(
            Optional<Category> category,
            String query,
            Pageable pageable
    ) {
        return productRepository.findByCategoryAndNameOrDescription(category
                        .map(Category::getId)
                        .orElse(null),
                query, pageable);
    }
}
