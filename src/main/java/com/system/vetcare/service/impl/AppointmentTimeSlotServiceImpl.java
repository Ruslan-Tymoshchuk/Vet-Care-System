package com.system.vetcare.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.AppointmentTimeSlot;
import com.system.vetcare.payload.response.AppointmentTimeSlotResponse;
import com.system.vetcare.repository.AppointmentTimeSlotRepository;
import com.system.vetcare.service.AppointmentTimeSlotService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentTimeSlotServiceImpl implements AppointmentTimeSlotService {

    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm");

    private final AppointmentTimeSlotRepository appointmentTimeSlotRepository;

    @Override
    public List<AppointmentTimeSlotResponse> findAll() {
        return appointmentTimeSlotRepository.findAll().stream().map(appointmentTimeSlot -> toDto(appointmentTimeSlot))
                .toList();
    }

    private AppointmentTimeSlotResponse toDto(AppointmentTimeSlot appointmentTimeSlot) {
        return new AppointmentTimeSlotResponse(appointmentTimeSlot.getId(),
                appointmentTimeSlot.getStartTime().format(TIME_FORMAT),
                appointmentTimeSlot.getEndTime().format(TIME_FORMAT));
    }

}