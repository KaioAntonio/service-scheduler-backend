package dev.kaio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaio.dto.user.UserRequestDTO;
import dev.kaio.dto.user.UserResponseDTO;
import dev.kaio.model.Users;
import dev.kaio.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final ObjectMapper objectMapper;

    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        Users user = objectMapper.convertValue(userRequestDTO, Users.class);
        user.setPassword(BCrypt.hashpw(userRequestDTO.getPassword(), BCrypt.gensalt()));

        repository.persist(user);
        return objectMapper.convertValue(user, UserResponseDTO.class);
    }
}
