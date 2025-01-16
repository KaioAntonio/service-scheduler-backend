package dev.kaio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaio.config.ServiceException;
import dev.kaio.dto.appointment.AppointmentRequestDTO;
import dev.kaio.dto.appointment.AppointmentResponseDTO;
import dev.kaio.dto.user.UserResponseDTO;
import dev.kaio.enums.UserTypeEnum;
import dev.kaio.model.Appointment;
import dev.kaio.repository.AppointmentRepository;
import dev.kaio.util.PageUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final ObjectMapper objectMapper;

    public PageUtil<AppointmentResponseDTO> findAllAppointments(int page, int size) {
        List<Appointment> appointments = repository.findAll().stream().toList();

        List<AppointmentResponseDTO> responseList = appointments.stream()
                .map(appointment -> objectMapper.convertValue(appointment, AppointmentResponseDTO.class))
                .toList();

        long totalElements = repository.count();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageUtil<>(responseList, totalElements, totalPages, page, size);
    }

    public PageUtil<AppointmentResponseDTO> findAllAppointmentsByClient(Long idUser, int page, int size) {
        List<Appointment> appointments = repository.findByClient(idUser);

        List<AppointmentResponseDTO> responseList = appointments.stream()
                .map(appointment -> objectMapper.convertValue(appointment, AppointmentResponseDTO.class))
                .toList();

        long totalElements = repository.count("client_id", idUser);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageUtil<>(responseList, totalElements, totalPages, page, size);
    }

    public PageUtil<AppointmentResponseDTO> findAllAppointmentsByProfessional(Long idProfessional, int page, int size) {
        List<Appointment> appointments = repository.findByProfessional(idProfessional);

        List<AppointmentResponseDTO> responseList = appointments.stream()
                .map(appointment -> objectMapper.convertValue(appointment, AppointmentResponseDTO.class))
                .toList();

        long totalElements = repository.count("profissional_id", idProfessional);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageUtil<>(responseList, totalElements, totalPages, page, size);
    }

    public AppointmentResponseDTO create(AppointmentRequestDTO appointmentRequestDTO) throws ServiceException {
        validateClientAndProfessional(appointmentRequestDTO);

        Appointment appointment = objectMapper.convertValue(appointmentRequestDTO, Appointment.class);
        repository.persist(appointment);

        return objectMapper.convertValue(appointment, AppointmentResponseDTO.class);
    }

    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO appointmentRequestDTO) throws ServiceException {
        findById(id);
        validateClientAndProfessional(appointmentRequestDTO);

        Appointment appointment = objectMapper.convertValue(appointmentRequestDTO, Appointment.class);
        appointment.setId(id);
        repository.updateAppointment(appointment);

        return objectMapper.convertValue(appointment, AppointmentResponseDTO.class);
    }

    public void delete(Long id) throws ServiceException {
        Appointment appointment = findById(id);
        repository.delete(appointment);
    }

    public Appointment findById(Long id) throws ServiceException {
        Appointment appointment = repository.findById(id);

        if(appointment == null) {
            throw new ServiceException("Agendamento inexistente!");
        }
        return appointment;
    }

    private void validateClientAndProfessional(AppointmentRequestDTO appointmentRequestDTO) throws ServiceException {
        if(repository.findByClient(appointmentRequestDTO.getClientId()).isEmpty() ||
                repository.findByProfessional(appointmentRequestDTO.getProfessionalId()).isEmpty()){
            throw new ServiceException("Informe um cliente e profissional existente.");
        }
    }
}
