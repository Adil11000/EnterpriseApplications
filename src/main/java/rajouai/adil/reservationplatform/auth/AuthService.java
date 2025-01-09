package rajouai.adil.reservationplatform.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.user.User;
import rajouai.adil.reservationplatform.user.UserRepository;

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
        // Hash the password before saving it, to avoid storing it in plain
        // text, the BCryptPasswordEncoder also generates a random salt
        String hashedPassword = new BCryptPasswordEncoder().encode(
                data.password());
        User newUser = new User(data.email(), hashedPassword);
        return repository.save(newUser);
    }
}
