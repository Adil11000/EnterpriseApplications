package rajouai.adil.reservationplatform.reservation;

import jakarta.persistence.*;
import rajouai.adil.reservationplatform.product.Product;

@Entity
public class ReservationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private int quantity;

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
