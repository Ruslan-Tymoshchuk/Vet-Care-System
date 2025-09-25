package com.system.vetcare.payload.response;

public record AppointmentResponse(
        Integer id,
        String visitDate,
        String time,
        String reason) {
}