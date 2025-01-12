package dev.kaio.model;

import dev.kaio.enums.AppointmentStatusEnum;
import dev.kaio.enums.UserTypeEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointment")
public class Appointment extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private AppointmentStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Users client;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private Users professional;
}
