package dev.kaio.repository;

import dev.kaio.enums.UserTypeEnum;
import dev.kaio.model.Users;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class UserRepository implements PanacheRepository<Users> {

    private final EntityManager entityManager;

    public Optional<Users> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public List<Users> findByType(UserTypeEnum type) {
        List<Users> users = find("type", type).list();
        if (users.isEmpty()) {
            System.out.println("Nenhum usuário encontrado para o tipo: " + type);
        }
        return users;
    }

    public Long countByType(UserTypeEnum type) {
        String countJpql = "SELECT COUNT(u) FROM Users u WHERE u.type = :type";
        Query query = entityManager.createQuery(countJpql);
        query.setParameter("type", type);
        return (Long) query.getSingleResult();
    }
}
