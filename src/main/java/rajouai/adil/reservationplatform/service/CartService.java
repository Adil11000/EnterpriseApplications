package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.model.Cart;
import rajouai.adil.reservationplatform.model.CartItem;
import rajouai.adil.reservationplatform.model.Product;
import rajouai.adil.reservationplatform.repository.CartRepository;
import rajouai.adil.reservationplatform.repository.ProductRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setCart(cart);

        cart.getItems().add(item);
        return cartRepository.save(cart);
    }
}
