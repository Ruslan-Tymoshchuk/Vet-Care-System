package com.system.vetcare.payload.response;

import java.util.List;

public record AvailableDateResponse(
        String date, 
        Boolean isAvailable,
        List<AppointmentTimeSlotResponse> timeSlots) {
}