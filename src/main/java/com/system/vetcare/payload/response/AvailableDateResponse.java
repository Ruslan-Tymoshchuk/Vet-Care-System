package com.system.vetcare.payload.response;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import java.time.LocalDate;
import java.util.List;

public record AvailableDateResponse(
        String date, 
        Boolean isAvailable, 
        List<AppointmentTimeSlotResponse> timeSlots) {
    
    public AvailableDateResponse(LocalDate date, Boolean isAvailable, List<AppointmentTimeSlotResponse> timeSlots) {
        this(date.format(ISO_LOCAL_DATE), isAvailable, timeSlots);
    }
    
}