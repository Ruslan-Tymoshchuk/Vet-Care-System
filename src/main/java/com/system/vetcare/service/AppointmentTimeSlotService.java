package com.system.vetcare.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.system.vetcare.domain.Appointment;
import com.system.vetcare.domain.AppointmentTimeSlot;
import com.system.vetcare.payload.response.AppointmentTimeSlotResponse;

public interface AppointmentTimeSlotService {

    AppointmentTimeSlot findById(Integer timeSlotId);

    List<AppointmentTimeSlot> findAll();
    
    Map<LocalDate, List<AppointmentTimeSlot>> extractBusyTimeSlots(List<Appointment> appointments);
    
    List<AppointmentTimeSlotResponse> buildTimeSlotsAvailability(List<AppointmentTimeSlot> allTimeSlots,
            List<AppointmentTimeSlot> busyTimeSlots);

}