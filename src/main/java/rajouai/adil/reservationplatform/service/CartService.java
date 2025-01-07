package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.*;
import rajouai.adil.reservationplatform.repository.CartRepository;
import rajouai.adil.reservationplatform.repository.ProductRepository;
import rajouai.adil.reservationplatform.repository.ReservationRepository;

import java.util.Date;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       ReservationRepository reservationRepository
    ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    public Cart addToCart(User user, Long productId, int quantity) {
        Cart cart = cartRepository
                .findByUserId(user.getId())
                .orElse(new Cart());
        cart.setUser(user);
        Product product = productRepository
                .findById(productId)
                .orElseThrow(
                        () -> new RuntimeException("Product not found"));
        List<CartItem> items = cart.getItems();
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        // If the product is already in the cart, update the quantity
        for (CartItem existingItem : items) {
            if (existingItem
                    .getProduct()
                    .getId()
                    .equals(productId)) {
                existingItem.setQuantity(
                        existingItem.getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }
        item.setCart(cart);
        items.add(item);
        cart.setItems(items);
        return cartRepository.save(cart);
    }

    public Cart removeFromCart(User user, Long productId, int quantity) {
        Cart cart = cartRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            if (item
                    .getProduct()
                    .getId()
                    .equals(productId)) {
                if (item.getQuantity() > quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                } else {
                    items.remove(item);
                }
                cart.setItems(items);
                return cartRepository.save(cart);
            }
        }
        throw new RuntimeException("Product not found in cart");
    }

    public Cart getCart(User user) {
        return cartRepository
                .findByUserId(user.getId())
                .orElse(new Cart());
    }

    public Reservation checkoutCart(User user, Date reservedUntil) {
        Cart cart = cartRepository
                .findByUserId(user.getId())
                .orElse(new Cart());
        if (cart
                .getItems()
                .isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        Reservation reservation = new Reservation();
        List<ReservationItem> items = cart
                .getItems()
                .stream()
                .map(item -> {
                    ReservationItem reservationItem = new ReservationItem();
                    reservationItem.setProduct(item.getProduct());
                    reservationItem.setReservation(reservation);
                    reservationItem.setQuantity(item.getQuantity());
                    return reservationItem;
                })
                .toList();
        reservation.setUser(user);
        reservation.setReturned(false);
        reservation.setItems(items);
        reservation.setReservedUntil(reservedUntil);

        cartRepository.delete(cart);
        return reservationRepository.save(reservation);
    }

}

