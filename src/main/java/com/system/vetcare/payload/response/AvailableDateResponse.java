package com.system.vetcare.payload.response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record AvailableDateResponse(
        String date, 
        Boolean isAvailable, 
        List<AppointmentTimeSlotResponse> timeSlots) {
    
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yy");
    
    public AvailableDateResponse(LocalDate date, Boolean isAvailable, List<AppointmentTimeSlotResponse> timeSlots) {
        this(date.format(DATE_FORMAT), isAvailable, timeSlots);
    }
    
}