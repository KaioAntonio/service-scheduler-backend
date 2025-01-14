package dev.kaio.util;

import dev.kaio.model.Users;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtUtil {

    private static Logger logger = LogManager.getLogger(JwtUtil.class);

    public String generateToken(Users user) {
        Set<String> roles = new HashSet<>();
        roles.add(String.valueOf(user.getType()));
        String token = Jwt.issuer("login-quarkus-scheduler")
                .subject(user.getEmail())
                .groups(roles)
                .expiresAt(Instant.now().plusSeconds(36000))
                .sign();
        logger.info("Token: " + token);
        return token;
    }
}
