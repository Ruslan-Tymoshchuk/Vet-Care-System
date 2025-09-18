package com.system.vetcare.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.payload.response.AppointmentTimeSlotResponse;
import com.system.vetcare.service.AppointmentTimeSlotService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AppointmentTimeSlotController {

	public static final String URL_TIMESLOTS_ALL = "/api/v1/time-slots/all";

	private final AppointmentTimeSlotService timeSlotService;

	@GetMapping(URL_TIMESLOTS_ALL)
	public ResponseEntity<List<AppointmentTimeSlotResponse>> findAll() {
		return ResponseEntity
			     .ok(timeSlotService.findAll());
	}

}