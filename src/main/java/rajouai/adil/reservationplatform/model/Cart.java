package rajouai.adil.reservationplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart implements Serializable  {
    private final List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Cart addItem(CartItem item) {
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
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
        return this;
    }

    public Cart removeItem(CartItem item) {
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
        if (existing != null) {
            if (existing.getQuantity() > item.getQuantity()) {
                existing.setQuantity(existing.getQuantity() - item.getQuantity());
            } else {
                items.remove(existing);
            }
        }
        return this;
    }

}
