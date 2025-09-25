package com.system.vetcare.service.impl;

import static java.lang.String.format;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.AppointmentTimeSlot;
import com.system.vetcare.exception.EntityNotFoundException;
import com.system.vetcare.payload.response.AppointmentTimeSlotResponse;
import com.system.vetcare.repository.AppointmentTimeSlotRepository;
import com.system.vetcare.service.AppointmentTimeSlotService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentTimeSlotServiceImpl implements AppointmentTimeSlotService {

    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm");
    public static final String APPOINTMENT_TIME_SLOT_WITH_ID_NOT_FOUND = "Appointment time slot with [id: %s] not found.";

    private final AppointmentTimeSlotRepository appointmentTimeSlotRepository;

    @Override
    public List<AppointmentTimeSlotResponse> findAll() {
        return appointmentTimeSlotRepository.findAll().stream().map(this::toDto).toList();
    }

    private AppointmentTimeSlotResponse toDto(AppointmentTimeSlot appointmentTimeSlot) {
        return new AppointmentTimeSlotResponse(appointmentTimeSlot.getId(),
                appointmentTimeSlot.getStartTime().format(TIME_FORMAT),
                appointmentTimeSlot.getEndTime().format(TIME_FORMAT));
    }

    @Override
    public AppointmentTimeSlot findById(Integer id) {
        return appointmentTimeSlotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(APPOINTMENT_TIME_SLOT_WITH_ID_NOT_FOUND, id)));
    }

}