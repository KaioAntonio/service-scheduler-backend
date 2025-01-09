package dev.kaio.service;

import dev.kaio.config.ServiceException;
import dev.kaio.model.Users;
import dev.kaio.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public String authenticate(String email, String password) throws ServiceException {
        Users user = userRepository.findByEmail(email).orElseThrow(
                () -> new ServiceException("Invalid email or password")
        );

        return generateToken(user.getEmail());

    }

    private String generateToken(String email) {
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        return Jwt.issuer("login-quarkus-scheduler")
                .subject(email)
                .groups(roles)
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();
    }
}
