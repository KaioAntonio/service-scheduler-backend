package dev.kaio.service;

import dev.kaio.config.ServiceException;
import dev.kaio.model.Users;
import dev.kaio.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;

    public String authenticate(String email, String password) throws ServiceException {
        Users user = userRepository.findByEmail(email).orElseThrow(
                () -> new ServiceException("Invalid email or password")
        );

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new ServiceException("Invalid email or password");
        }

        return generateToken(user);

    }

    private String generateToken(Users user) {
        Set<String> roles = new HashSet<>();
        roles.add(String.valueOf(user.getType()));
        return Jwt.issuer("login-quarkus-scheduler")
                .subject(user.getEmail())
                .groups(roles)
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();
    }
}
