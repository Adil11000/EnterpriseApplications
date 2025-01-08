package rajouai.adil.reservationplatform.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rajouai.adil.reservationplatform.dto.CartItemDto;
import rajouai.adil.reservationplatform.dto.CheckoutDto;
import rajouai.adil.reservationplatform.model.Cart;
import rajouai.adil.reservationplatform.model.Reservation;
import rajouai.adil.reservationplatform.model.User;
import rajouai.adil.reservationplatform.service.CartService;

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
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
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
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
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
        Cart cart = (Cart) session.getAttribute("cart");
        cart = cartService.removeFromCart(cart, body.productId(),
                body.quantity());
        session.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Reservation> checkout(
            HttpServletRequest request,
            @RequestBody CheckoutDto body
    ) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Reservation res = cartService.checkoutCart(user, cart,
                body.reservedUntil());
        session.setAttribute("cart", new Cart());
        return ResponseEntity.ok(res);
    }
}

