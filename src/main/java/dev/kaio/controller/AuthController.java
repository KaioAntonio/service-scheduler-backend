package dev.kaio.controller;

import dev.kaio.config.ServiceException;
import dev.kaio.dto.login.LoginRequestDTO;
import dev.kaio.dto.login.TokenResponse;
import dev.kaio.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.spi.validation.ValidateRequest;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @ValidateRequest
    public Response login(LoginRequestDTO loginRequest) throws ServiceException {
        try {
            String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return Response.ok(new TokenResponse(token)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }
}
