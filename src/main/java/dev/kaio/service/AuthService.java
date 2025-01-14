package dev.kaio.service;

import dev.kaio.config.ServiceException;
import dev.kaio.model.Users;
import dev.kaio.repository.UserRepository;
import dev.kaio.util.JwtUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public String authenticate(String email, String password) throws ServiceException {
        Users user = userRepository.findByEmail(email).orElseThrow(
                () -> new ServiceException("Invalid email or password")
        );

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new ServiceException("Invalid email or password");
        }

        return jwtUtil.generateToken(user);

    }
}
