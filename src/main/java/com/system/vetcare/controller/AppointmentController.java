package com.system.vetcare.controller;

import static com.system.vetcare.controller.constants.AuthorityTitle.OWNER;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.payload.request.AppointmentRequest;
import com.system.vetcare.payload.response.AppointmentResponse;
import com.system.vetcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    public static final String URL_APPOINTMENTS = "/api/v1/appointments";

    private final AppointmentService appointmentService;

    @Secured({ OWNER })
    @PostMapping(URL_APPOINTMENTS)
    public ResponseEntity<AppointmentResponse> save(@RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity
                 .status(CREATED)
                 .body(appointmentService.save(appointmentRequest));
    }
 
}