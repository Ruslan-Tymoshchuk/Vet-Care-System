package com.system.vetcare.service;

import java.time.LocalDate;
import java.util.List;
import com.system.vetcare.domain.Appointment;
import com.system.vetcare.payload.request.AppointmentRequest;
import com.system.vetcare.payload.response.AppointmentResponse;

public interface AppointmentService {

    AppointmentResponse save(AppointmentRequest appointmentRequest);

    List<Appointment> findByVeterinarianInDateInterval(Integer id, LocalDate beginDate, LocalDate completeDate);
    
}