package dev.kaio.controller;

import dev.kaio.config.ServiceException;
import dev.kaio.dto.user.PasswordRequestDTO;
import dev.kaio.dto.user.UserRequestDTO;
import dev.kaio.dto.user.UserResponseDTO;
import dev.kaio.service.UserService;
import dev.kaio.util.PageUtil;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestResponse;

import javax.annotation.security.RolesAllowed;

import static org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import static org.jboss.resteasy.reactive.RestResponse.Status;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @POST
    @Path("/")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO userRequestDTO) throws ServiceException {
        return ResponseBuilder.create(Status.CREATED, userService.create(userRequestDTO)).build();
    }


    @GET
    @Path("/")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<PageUtil<UserResponseDTO>> findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        return ResponseBuilder.ok(userService.findAllProfessionals(page, size)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<UserResponseDTO> update(
            @PathParam("id") Long id,
            @RequestBody @Valid UserRequestDTO userRequestDTO
    ) throws ServiceException {
        return ResponseBuilder.ok(userService.update(id, userRequestDTO)).build();
    }

    @PUT
    @Path("updatePassword/{id}")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<UserResponseDTO> updatePassword(
            @PathParam("id") Long id,
            @RequestBody @Valid PasswordRequestDTO passwordRequestDTO
    ) throws ServiceException {
        return ResponseBuilder.ok(userService.updatePassword(id, passwordRequestDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<Object> delete(
            @PathParam("id") Long id
    ) throws ServiceException {
        userService.delete(id);
        return ResponseBuilder.noContent().build();
    }
}
