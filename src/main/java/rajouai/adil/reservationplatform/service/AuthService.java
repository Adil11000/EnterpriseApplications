package rajouai.adil.reservationplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.dto.AuthDto;
import rajouai.adil.reservationplatform.model.User;
import rajouai.adil.reservationplatform.repository.UserRepository;

import java.security.InvalidParameterException;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return repository.findByEmail(email);
    }

    public UserDetails signUp(AuthDto data) throws InvalidParameterException {
        if (repository.findByEmail(data.email()) != null) {
            throw new InvalidParameterException("Username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), encryptedPassword);
        return repository.save(newUser);
    }
}
