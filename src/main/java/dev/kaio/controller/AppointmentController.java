package dev.kaio.controller;

import dev.kaio.config.ServiceException;
import dev.kaio.dto.appointment.AppointmentRequestDTO;
import dev.kaio.dto.appointment.AppointmentResponseDTO;
import dev.kaio.service.AppointmentService;
import dev.kaio.util.PageUtil;
import jakarta.annotation.security.RolesAllowed;
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

import static org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import static org.jboss.resteasy.reactive.RestResponse.Status;

@Path("/appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @POST
    @Path("/")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<AppointmentResponseDTO> create(@RequestBody @Valid AppointmentRequestDTO appointmentRequestDTO)
            throws ServiceException {
        return ResponseBuilder.create(Status.CREATED, appointmentService.create(appointmentRequestDTO)).build();
    }

    @GET
    @Path("/")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<PageUtil<AppointmentResponseDTO>> findAll(
            @QueryParam("page") int page,
            @QueryParam("size") int size
    ) {
        return ResponseBuilder.ok(appointmentService.findAllAppointments(page, size)).build();
    }

    @GET
    @Path("/client/{id}")
    @RolesAllowed("CLIENTE")
    public RestResponse<PageUtil<AppointmentResponseDTO>> findAllByClient(
            @PathParam("id") Long id,
            @QueryParam("page") int page,
            @QueryParam("size") int size
    ) {
        return ResponseBuilder.ok(appointmentService.findAllAppointmentsByClient(id, page, size)).build();
    }

    @GET
    @Path("/professional/{id}")
    @RolesAllowed("PROFISSIONAL")
    public RestResponse<PageUtil<AppointmentResponseDTO>> findAllByProfessional(
            @PathParam("id") Long id,
            @QueryParam("page") int page,
            @QueryParam("size") int size
    ) {
        return ResponseBuilder.ok(appointmentService.findAllAppointmentsByProfessional(id, page, size)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<AppointmentResponseDTO> update(
            @PathParam("id") Long id,
            @RequestBody @Valid AppointmentRequestDTO appointmentRequestDTO
    ) throws ServiceException {
        return ResponseBuilder.ok(appointmentService.update(id, appointmentRequestDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"PROFISSIONAL", "CLIENTE"})
    public RestResponse<Object> delete(@PathParam("id") Long id) throws ServiceException {
        appointmentService.delete(id);
        return ResponseBuilder.noContent().build();
    }
}

