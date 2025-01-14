package dev.kaio.controller;

import dev.kaio.config.ServiceException;
import dev.kaio.dto.login.LoginRequestDTO;
import dev.kaio.dto.login.TokenResponse;
import dev.kaio.service.AuthService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @POST
    @Path("/login")
    public RestResponse<TokenResponse> login(@RequestBody @Valid LoginRequestDTO loginRequest) throws ServiceException {
        String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseBuilder.ok(new TokenResponse(token)).build();
    }
}
