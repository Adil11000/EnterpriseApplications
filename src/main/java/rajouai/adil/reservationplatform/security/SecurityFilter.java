package rajouai.adil.reservationplatform.security;

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
import rajouai.adil.reservationplatform.model.Cart;
import rajouai.adil.reservationplatform.model.User;
import rajouai.adil.reservationplatform.repository.UserRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

// config/auth/SecurityFilter.java
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenProvider tokenService;
    @Autowired
    UserRepository userRepository;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (isExcluded(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = this.recoverToken(request);
        if (token == null) {

            return;
        }
        String login = tokenService.validateToken(token);
        User user = userRepository.findByEmail(login);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
            Cart cart = new Cart();
            session.setAttribute("cart", cart);
            session.setAttribute("user", user.getEmail());
        } else {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isExcluded(String path) {
        return UrlConstants.AUTH_WHITELIST
                .stream()
                .anyMatch(pattern -> pathMatcher.match(pattern,
                        path));
    }


    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}