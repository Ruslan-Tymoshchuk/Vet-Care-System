package com.system.vetcare.service.impl;

import static java.lang.String.format;
import java.time.LocalDate;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Appointment;
import com.system.vetcare.domain.AppointmentTimeSlot;
import com.system.vetcare.exception.EntityNotFoundException;
import com.system.vetcare.payload.response.AppointmentTimeSlotResponse;
import com.system.vetcare.repository.AppointmentTimeSlotRepository;
import com.system.vetcare.service.AppointmentTimeSlotService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentTimeSlotServiceImpl implements AppointmentTimeSlotService {

    public static final String APPOINTMENT_TIME_SLOT_WITH_ID_NOT_FOUND = "Appointment time slot with [id: %s] not found.";

    private final AppointmentTimeSlotRepository appointmentTimeSlotRepository;

    @Override
    public List<AppointmentTimeSlotResponse> findAll() {
        return appointmentTimeSlotRepository.findAll().stream()
                .map(appointmentTimeSlot -> new AppointmentTimeSlotResponse(appointmentTimeSlot, true)).toList();
    }
    
    @Override
    public AppointmentTimeSlot findById(Integer id) {
        return appointmentTimeSlotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(APPOINTMENT_TIME_SLOT_WITH_ID_NOT_FOUND, id)));
    }

    @Override
    public Map<LocalDate, List<AppointmentTimeSlot>> extractBusyTimeSlots(List<Appointment> appointments) {
        return appointments.stream()
                .collect(groupingBy(Appointment::getVisitDate, mapping(Appointment::getTimeSlot, toList())));
    }
    
    @Override
    public List<AppointmentTimeSlotResponse> buildTimeSlotsAvailability(List<AppointmentTimeSlot> busyTimeSlots) {
        return appointmentTimeSlotRepository.findAll().stream()
                .map(appointmentTimeSlot -> new AppointmentTimeSlotResponse(appointmentTimeSlot,
                        !busyTimeSlots.contains(appointmentTimeSlot)))
                .toList();
    }

}