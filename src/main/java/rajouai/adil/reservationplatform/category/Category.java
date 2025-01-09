package rajouai.adil.reservationplatform.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import rajouai.adil.reservationplatform.product.Product;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
