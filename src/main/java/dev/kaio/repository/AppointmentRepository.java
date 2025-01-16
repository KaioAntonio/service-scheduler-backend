package dev.kaio.repository;

import dev.kaio.model.Appointment;
import dev.kaio.model.Users;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AppointmentRepository implements PanacheRepository<Appointment> {
    public List<Appointment> findByClient(Long id) {
        return list("client_id", id);
    }

    public List<Appointment> findByProfessional(Long id) {
        return list("profissional_id", id);
    }

    public Appointment updateAppointment(Appointment appointment) {
        update("appointmentdate = ?1, description = ?2, status = ?3, client = ?4, professional = ?5 where id = ?6",
                appointment.getAppointmentDate(),
                appointment.getDescription(),
                appointment.getStatus(),
                appointment.getClient(),
                appointment.getProfessional(),
                appointment.getId());
        return findById(appointment.getId());
    }
}
