package com.system.vetcare.controller;

import static com.system.vetcare.controller.constants.AuthorityTitle.*;
import static java.util.Collections.emptyList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.domain.AppointmentTimeSlot;
import com.system.vetcare.payload.response.AppointmentTimeSlotResponse;
import com.system.vetcare.service.AppointmentTimeSlotService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AppointmentTimeSlotController {

    public static final String URL_TIMESLOTS_ALL = "/api/v1/time-slots/all";

    private final AppointmentTimeSlotService timeSlotService;

    @Secured({ MANAGER })
    @GetMapping(URL_TIMESLOTS_ALL)
    public ResponseEntity<List<AppointmentTimeSlotResponse>> findAll() {
        List<AppointmentTimeSlot> allTimeSlots = timeSlotService.findAll();
        List<AppointmentTimeSlot> busyTimeSlots = emptyList();
        return ResponseEntity.ok(timeSlotService.buildTimeSlotsAvailability(allTimeSlots, busyTimeSlots));
    }

}