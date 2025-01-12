package dev.kaio.controller;

import dev.kaio.dto.user.UserRequestDTO;
import dev.kaio.dto.user.UserResponseDTO;
import dev.kaio.service.UserService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.spi.validation.ValidateRequest;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @POST
    @Path("/")
    @ValidateRequest
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        return userService.create(userRequestDTO);
    }
}
