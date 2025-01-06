package rajouai.adil.reservationplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rajouai.adil.reservationplatform.model.Cart;
import rajouai.adil.reservationplatform.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long userId,
                                          @RequestParam Long productId,
                                          @RequestParam int quantity) {
        Cart cart = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }
}

