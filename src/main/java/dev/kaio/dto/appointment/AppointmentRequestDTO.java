package dev.kaio.dto.appointment;

import dev.kaio.enums.AppointmentStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {

    private LocalDateTime appointmentDate;
    private String description;
    private AppointmentStatusEnum status;
    private Long clientId;
    private Long professionalId;
}
