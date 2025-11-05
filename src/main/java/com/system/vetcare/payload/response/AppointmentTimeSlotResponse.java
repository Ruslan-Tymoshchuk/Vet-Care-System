package com.system.vetcare.payload.response;

import java.time.format.DateTimeFormatter;
import com.system.vetcare.domain.AppointmentTimeSlot;
import lombok.Getter;

@Getter
public class AppointmentTimeSlotResponse {

    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm");

    private final Integer id;
    private final String startTime;
    private final String endTime;
    private final Boolean isAvailable;

    public AppointmentTimeSlotResponse(AppointmentTimeSlot appointmentTimeSlot, Boolean isAvailable) {
        this.id = appointmentTimeSlot.getId();
        this.startTime = appointmentTimeSlot.getStartTime().format(TIME_FORMAT);
        this.endTime = appointmentTimeSlot.getEndTime().format(TIME_FORMAT);
        this.isAvailable = isAvailable;
    }

}