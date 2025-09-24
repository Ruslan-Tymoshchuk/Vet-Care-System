package com.system.vetcare.service;

import com.system.vetcare.payload.request.AppointmentRequest;
import com.system.vetcare.payload.response.AppointmentResponse;

public interface AppointmentService {

    AppointmentResponse save(AppointmentRequest appointmentRequest);

}