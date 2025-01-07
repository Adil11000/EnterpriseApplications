package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.SlimCategoryProjection;
import rajouai.adil.reservationplatform.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<SlimCategoryProjection> getAllCategories() {
        return categoryRepository.findAllCategoryNames();
    }
}
