package dev.kaio.config;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomGlobalExceptionHandler implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException exception)
    {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
