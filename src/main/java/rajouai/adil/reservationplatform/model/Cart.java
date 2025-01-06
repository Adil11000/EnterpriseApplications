package rajouai.adil.reservationplatform.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;

    public Collection<CartItem> getItems() {
        return items;
    }
}
