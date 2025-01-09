package rajouai.adil.reservationplatform.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import rajouai.adil.reservationplatform.cart.Cart;
import rajouai.adil.reservationplatform.common.Constants;
import rajouai.adil.reservationplatform.user.User;
import rajouai.adil.reservationplatform.user.UserRepository;

import java.io.IOException;
import java.util.Arrays;

// config/auth/SecurityFilter.java
@Component
public class AuthSessionFilter extends OncePerRequestFilter {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (isExcluded(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        // Get the session, if it exists
        HttpSession session = request.getSession(false);
        if (session == null) {
            // Should never happen because we have `SessionCreationPolicy.ALWAYS`
            filterChain.doFilter(request, response);
            return;
        }
        // Get the user from the session
        User user = (User) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_USER);
        if (user == null) {
            // If the user is not in the session, it means they haven't logged in
            filterChain.doFilter(request, response);
            return;
        }
        // Get the cart from the session
        Cart cart = (Cart) session.getAttribute(
                Constants.SESSION_ATTRIBUTE_CART);
        if (cart == null) {
            // If the cart is not in the session, create a new one
            cart = new Cart();
            session.setAttribute(Constants.SESSION_ATTRIBUTE_CART, cart);
        }
        // Set the user in the security context
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean isExcluded(String path) {
        return Arrays
                .stream(Constants.AUTH_WHITELIST)
                .anyMatch(
                        pattern -> pathMatcher.match(pattern, path));
    }
}