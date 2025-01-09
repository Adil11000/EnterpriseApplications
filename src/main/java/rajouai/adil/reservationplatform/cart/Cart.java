package rajouai.adil.reservationplatform.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private final List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        CartItem existing = items
                .stream()
                .filter(i -> i
                        .getProduct()
                        .getId()
                        .equals(item
                                .getProduct()
                                .getId()))
                .findFirst()
                .orElse(null);
        // If the item is not in the cart, add it, otherwise increase the quantity
        if (existing == null) {
            items.add(item);
        } else {
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        }
    }

    public void removeItem(CartItem item) {
        // Find the item in the cart, if it exists
        CartItem existing = items
                .stream()
                .filter(i -> i
                        .getProduct()
                        .getId()
                        .equals(item
                                .getProduct()
                                .getId()))
                .findFirst()
                .orElse(null);
        if (existing == null) {
            // If the item is not in the cart, do nothing
            return;
        }
        // If the item is in the cart, decrease the quantity, if we reach 0, remove it
        if (existing.getQuantity() > item.getQuantity()) {
            existing.setQuantity(
                    existing.getQuantity() - item.getQuantity());
        } else {
            items.remove(existing);
        }
    }

}
