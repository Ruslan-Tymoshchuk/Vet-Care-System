package com.system.vetcare.service;

import java.util.List;
import com.system.vetcare.domain.AppointmentTimeSlot;
import com.system.vetcare.payload.response.AppointmentTimeSlotResponse;

public interface AppointmentTimeSlotService {

	List<AppointmentTimeSlotResponse> findAll();

    AppointmentTimeSlot findById(Integer timeSlotId);

}