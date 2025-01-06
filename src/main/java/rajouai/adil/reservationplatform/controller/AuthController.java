package rajouai.adil.reservationplatform.controller;

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
import rajouai.adil.reservationplatform.dto.AuthDto;
import rajouai.adil.reservationplatform.dto.JwtDto;
import rajouai.adil.reservationplatform.model.User;
import rajouai.adil.reservationplatform.security.TokenProvider;
import rajouai.adil.reservationplatform.service.AuthService;

// controllers/AuthController.java
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService service;
    @Autowired
    private TokenProvider tokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AuthDto data) {
        User user = (User) service.signUp(data);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new JwtDto(tokenService.generateAccessToken(user)));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtDto> signIn(@RequestBody AuthDto data) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        return ResponseEntity.ok(new JwtDto(tokenService.generateAccessToken((User) auth.getPrincipal())));
    }
}
