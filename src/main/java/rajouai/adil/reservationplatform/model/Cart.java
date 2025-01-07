package rajouai.adil.reservationplatform.model;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    public List<CartItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return new ArrayList<>(items);
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void clearItems() {
        items.clear();
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
