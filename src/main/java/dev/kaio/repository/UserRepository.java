package dev.kaio.repository;

import dev.kaio.model.Users;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<Users> {

    public Optional<Users> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
