package rajouai.adil.reservationplatform.cart;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.product.Product;
import rajouai.adil.reservationplatform.product.ProductRepository;
import rajouai.adil.reservationplatform.reservation.Reservation;
import rajouai.adil.reservationplatform.reservation.ReservationItem;
import rajouai.adil.reservationplatform.reservation.ReservationRepository;
import rajouai.adil.reservationplatform.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        // Copy cart items to reservation items
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

