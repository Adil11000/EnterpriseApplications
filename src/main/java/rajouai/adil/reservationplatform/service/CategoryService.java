package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.SlimCategoryProjection;
import rajouai.adil.reservationplatform.model.Product;
import rajouai.adil.reservationplatform.repository.CategoryRepository;
import rajouai.adil.reservationplatform.repository.ProductRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<SlimCategoryProjection> getAllCategories() {
        return categoryRepository.findAllCategoryNames();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
