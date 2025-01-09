package rajouai.adil.reservationplatform.cart;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rajouai.adil.reservationplatform.common.Constants;
import rajouai.adil.reservationplatform.reservation.Reservation;
import rajouai.adil.reservationplatform.user.User;

@RestController
@RequestMapping("/api/v1/user/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_CART);
        if (cart == null) {
            cart = new Cart();
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            HttpServletRequest request,
            @RequestBody @NotNull
            CartItemDto body
    ) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_CART);
        cart = cartService.addToCart(cart, body.productId(), body.quantity());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(
            HttpServletRequest request,
            @RequestBody
            @NotNull
            CartItemDto body
    ) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_CART);
        cart = cartService.removeFromCart(cart, body.productId(),
                body.quantity());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Reservation> checkout(
            HttpServletRequest request,
            @RequestBody CheckoutDto body
    ) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_USER);
        Cart cart = (Cart) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_CART);
        Reservation res = cartService.checkoutCart(user, cart,
                body.reservedUntil());
        session.setAttribute(Constants.SESSION_ATTRIBUTE_CART, new Cart());
        return ResponseEntity.ok(res);
    }
}

