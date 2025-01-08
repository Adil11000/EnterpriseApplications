package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.*;
import rajouai.adil.reservationplatform.repository.ProductRepository;
import rajouai.adil.reservationplatform.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    public CartService(
            ProductRepository productRepository,
            ReservationRepository reservationRepository
    ) {
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    public Cart addToCart(Cart cart, Long productId, int quantity) {
        CartItem item = new CartItem();
        Product product = productRepository
                .findById(productId)
                .orElseThrow();
        item.setProduct(product);
        item.setQuantity(quantity);
        cart.addItem(item);
        return cart;
    }

    public Cart removeFromCart(Cart cart, Long productId, int quantity) {
        CartItem item = new CartItem();
        Product product = productRepository
                .findById(productId)
                .orElseThrow();
        item.setProduct(product);
        item.setQuantity(quantity);
        cart.removeItem(item);
        return cart;
    }

    public Reservation checkoutCart(User user, Cart cart, Date reservedUntil) {
        if (cart
                .getItems()
                .isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setReturned(false);
        List<ReservationItem> items = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            ReservationItem reservationItem = new ReservationItem();
            reservationItem.setReservation(reservation);
            reservationItem.setProduct(item.getProduct());
            reservationItem.setQuantity(item.getQuantity());
            items.add(reservationItem);
        }
        reservation.setItems(items);
        reservation.setReservedUntil(reservedUntil);
        return reservationRepository.save(reservation);
    }

}

