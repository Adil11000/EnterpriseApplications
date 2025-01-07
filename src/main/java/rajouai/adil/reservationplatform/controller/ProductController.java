package rajouai.adil.reservationplatform.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rajouai.adil.reservationplatform.model.Category;
import rajouai.adil.reservationplatform.model.Product;
import rajouai.adil.reservationplatform.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam() Optional<String> query,
            @RequestParam() Optional<Category> category,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(20) Integer size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, sortDirection,
                sortBy);
        return productService.listProductUsingExample(pageable, category,
                query.orElse(""));
    }
}
