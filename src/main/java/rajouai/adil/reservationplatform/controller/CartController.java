package rajouai.adil.reservationplatform.controller;

import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<Cart> getCart() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestBody @NotNull
            CartItemDto body
    ) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.addToCart(user, body.productId(),
                body.quantity());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(
            @RequestBody
            @NotNull
            CartItemDto body
    ) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.removeFromCart(user, body.productId(),
                body.quantity());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Reservation> checkout(
            @RequestBody CheckoutDto body
    ) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        Reservation res = cartService.checkoutCart(user,
                body.reservedUntil());
        return ResponseEntity.ok(res);
    }
}

