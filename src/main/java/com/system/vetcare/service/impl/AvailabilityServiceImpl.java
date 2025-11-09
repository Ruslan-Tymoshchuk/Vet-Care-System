package com.system.vetcare.service.impl;

import static java.util.Collections.emptyList;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Appointment;
import com.system.vetcare.domain.AppointmentTimeSlot;
import com.system.vetcare.payload.response.AvailableDateResponse;
import com.system.vetcare.service.AppointmentService;
import com.system.vetcare.service.AppointmentTimeSlotService;
import com.system.vetcare.service.AvailabilityService;
import com.system.vetcare.service.LeaveService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AppointmentService appointmentService;
    private final AppointmentTimeSlotService appointmentTimeSlotService;
    private final LeaveService leaveService;

    @Override
    public List<AvailableDateResponse> calculateVeterinarianAvailabilitySlots(Integer id, LocalDate beginDate,
            LocalDate completeDate) {
        List<Appointment> appointments = appointmentService.findByVeterinarianInDateInterval(id, beginDate,
                completeDate);
        Map<LocalDate, List<AppointmentTimeSlot>> busyTimeSlots = appointmentTimeSlotService
                .extractBusyTimeSlots(appointments);
        Set<LocalDate> absentDays = leaveService.extractAbsentDays(id, beginDate, completeDate);
        List<AppointmentTimeSlot> allTimeSlots = appointmentTimeSlotService.findAll();
        return beginDate.datesUntil(completeDate.plusDays(1)).map(day -> {
            if (absentDays.contains(day)) {
                return new AvailableDateResponse(day.toString(), false, emptyList());
            } else {
                return new AvailableDateResponse(day.toString(), true, appointmentTimeSlotService
                        .buildTimeSlotsAvailability(allTimeSlots, busyTimeSlots.getOrDefault(day, emptyList())));
            }
        }).toList();
    }

}