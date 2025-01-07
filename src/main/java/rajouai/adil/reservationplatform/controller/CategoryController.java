package rajouai.adil.reservationplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rajouai.adil.reservationplatform.model.SlimCategoryProjection;
import rajouai.adil.reservationplatform.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<SlimCategoryProjection> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
