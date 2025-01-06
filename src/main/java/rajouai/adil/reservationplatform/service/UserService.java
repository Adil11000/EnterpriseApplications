package rajouai.adil.reservationplatform.service;

import org.springframework.stereotype.Service;
import rajouai.adil.reservationplatform.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

