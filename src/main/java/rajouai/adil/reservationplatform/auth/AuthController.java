package rajouai.adil.reservationplatform.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rajouai.adil.reservationplatform.common.Constants;
import rajouai.adil.reservationplatform.user.User;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            HttpServletRequest request,
            @RequestBody AuthDto data) {
        User user = (User) authService.signUp(data);
        HttpSession session = request.getSession();
        // Set the user in the session
        session.setAttribute(Constants.SESSION_ATTRIBUTE_USER, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            HttpServletRequest request,
            @RequestBody AuthDto data) {
        // Authenticate the user
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                data.email(), data.password());
        Authentication auth = authenticationManager.authenticate(
                authenticationToken);
        HttpSession session = request.getSession();
        // Set the user in the session
        session.setAttribute(Constants.SESSION_ATTRIBUTE_USER,
                auth.getPrincipal());
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Invalidate the session if it exists
            session.invalidate();
        }
        return ResponseEntity
                .ok()
                .build();
    }
}
