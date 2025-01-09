package rajouai.adil.reservationplatform.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rajouai.adil.reservationplatform.category.Category;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PagedModel<Product> getAllProducts(
            // What the user entered the search bar
            @RequestParam(defaultValue = "") String query,
            // The category the user selected
            @RequestParam() Optional<Category> category,
            // Pagination parameters
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(20) Integer size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        // Page numbers are 0-based in Spring Data, but on the frontend they are 1-based
        page = page - 1;
        // Create a Pageable object from the parameters
        Pageable pageable = PageRequest.of(page, size, sortDirection,
                sortBy);
        return new PagedModel<>(
                productService.listProductUsingExample(category,
                        query, pageable));
    }
}
