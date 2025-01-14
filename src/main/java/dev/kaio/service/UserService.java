package dev.kaio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaio.config.ServiceException;
import dev.kaio.dto.user.PasswordRequestDTO;
import dev.kaio.dto.user.UserRequestDTO;
import dev.kaio.dto.user.UserResponseDTO;
import dev.kaio.enums.UserTypeEnum;
import dev.kaio.model.Users;
import dev.kaio.repository.UserRepository;
import dev.kaio.util.PageUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final ObjectMapper objectMapper;

    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequestDTO) throws ServiceException {
        validateEmail(userRequestDTO);

        Users user = objectMapper.convertValue(userRequestDTO, Users.class);
        user.setPassword(BCrypt.hashpw(userRequestDTO.getPassword(), BCrypt.gensalt()));

        repository.persist(user);
        return objectMapper.convertValue(user, UserResponseDTO.class);
    }

    public PageUtil<UserResponseDTO> findAllProfessionals(Integer page, Integer size) {
        List<Users> users = repository.findByType(UserTypeEnum.PROFISSIONAL);

        List<UserResponseDTO> responseList = users.stream()
                .map(user -> objectMapper.convertValue(user, UserResponseDTO.class))
                .toList();

        Long totalElements = repository.countByType(UserTypeEnum.PROFISSIONAL);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageUtil<>(responseList, totalElements, totalPages, page, size);
    }

    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) throws ServiceException {
        Users user = findById(id);

        validateEmail(userRequestDTO);

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());

        repository.persist(user);
        return objectMapper.convertValue(user, UserResponseDTO.class);
    }

    @Transactional
    public void delete(Long id) throws ServiceException {
        Users user = findById(id);
        repository.delete(user);
    }

    @Transactional
    public UserResponseDTO updatePassword(Long id, PasswordRequestDTO password) throws ServiceException {
        Users user = findById(id);
        user.setPassword(BCrypt.hashpw(password.getPassword(), BCrypt.gensalt()));

        repository.persist(user);
        return objectMapper.convertValue(user, UserResponseDTO.class);
    }

    public Users findById(Long id) throws ServiceException {
        Users byId = repository.findById(id);

        if(byId == null) {
            throw new ServiceException("Usuário inexistente!");
        }
        return byId;
    }

    private void validateEmail(UserRequestDTO userRequestDTO) throws ServiceException {
        if(repository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new ServiceException("Email já existente!");
        }
    }
}
