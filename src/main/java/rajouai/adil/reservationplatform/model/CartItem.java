package rajouai.adil.reservationplatform.model;

import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Long id;
    private Product product;
    private int quantity;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
