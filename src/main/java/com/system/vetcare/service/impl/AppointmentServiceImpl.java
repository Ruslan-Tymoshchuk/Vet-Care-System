package com.system.vetcare.service.impl;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Appointment;
import com.system.vetcare.domain.enums.EAppointmentStatus;
import com.system.vetcare.exception.EntityNotFoundException;
import com.system.vetcare.payload.request.AppointmentRequest;
import com.system.vetcare.payload.response.AppointmentResponse;
import com.system.vetcare.repository.AppointmentRepository;
import com.system.vetcare.service.AppointmentService;
import com.system.vetcare.service.AppointmentTimeSlotService;
import com.system.vetcare.service.OwnerService;
import com.system.vetcare.service.PetService;
import com.system.vetcare.service.VeterinarianService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String APPOINTMENT_WITH_ID_NOT_FOUND = "Appointment with [id: %s] not found.";
    public static final String APPOINTMENT_TIME = "Start at %s End at %s";
    
    private final AppointmentRepository appointmentRepository;
    private final OwnerService ownerService;
    private final PetService petService;
    private final VeterinarianService veterinarianService;
    private final AppointmentTimeSlotService appointmentTimeSlotService;
    
    @Override
    public AppointmentResponse save(AppointmentRequest appointmentRequest) {
        Appointment appointment = toEntity(appointmentRequest);
        appointment.setStatus(EAppointmentStatus.BOOKED);
        return toDto(appointmentRepository.save(appointment));
    }

    private Appointment toEntity(AppointmentRequest appointmentRequest) {
        Integer id = appointmentRequest.id();
        Appointment appointment = Appointment
                                    .builder()
                                    .build();
        if(nonNull(id)) {
            appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(format(APPOINTMENT_WITH_ID_NOT_FOUND, id)));
        }
        Integer ownerId = appointmentRequest.ownerId();
        if(nonNull(ownerId)) {
            appointment.setOwner(ownerService.findById(ownerId));
        }
        Integer petId = appointmentRequest.petId();
        if(nonNull(petId)) {
            appointment.setPet(petService.findById(petId));
        }
        Integer veterinarianId = appointmentRequest.veterinarianId();
        if(nonNull(veterinarianId)) {
            appointment.setVeterinarian(veterinarianService.findById(veterinarianId));
        }
        appointment.setVisitDate(LocalDate.parse(appointmentRequest.visitDate(), DATE_FORMATTER));
        Integer timeSlotId = appointmentRequest.timeSlotId();
        if(nonNull(timeSlotId)) {
            appointment.setTimeSlot(appointmentTimeSlotService.findById(timeSlotId));
        }
        appointment.setReason(appointmentRequest.reason());
        return appointment; 
    }
    
    private AppointmentResponse toDto(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(), appointment.getVisitDate().format(DATE_FORMATTER), format(APPOINTMENT_TIME,
                        appointment.getTimeSlot().getStartTime(), appointment.getTimeSlot().getEndTime()),
                appointment.getReason());
    }

}