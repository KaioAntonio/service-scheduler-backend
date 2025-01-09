package dev.kaio.util;

import io.smallrye.jwt.build.Jwt;

import java.util.HashSet;
import java.util.Set;

public class JwtUtil {

    public static String generateToken(String email, String userType) {
        Set<String> roles = new HashSet<>();
        roles.add(userType);

        return Jwt.issuer("http://localhost:8080")
                .upn(email)
                .groups(roles)
                .expiresIn(3600)
                .sign();
    }
}
