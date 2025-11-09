package com.system.vetcare.controller;

import static com.system.vetcare.controller.constants.AuthorityTitle.OWNER;
import static java.util.Optional.ofNullable;
import static java.time.LocalDate.now;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.payload.response.AvailableDateResponse;
import com.system.vetcare.service.AvailabilityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AvailabilityController.URL_AVAILABILITY)
public class AvailabilityController {

    public static final String URL_AVAILABILITY = "/api/v1/availability";
    public static final String URL_AVAILABLE_DATE_SLOTS = "/available-date-slots";

    private final AvailabilityService availabilityService;

    @Secured({ OWNER })
    @GetMapping(URL_AVAILABLE_DATE_SLOTS)
    public ResponseEntity<List<AvailableDateResponse>> calculateVeterinarianAvailabilitySlots(
            @RequestParam("veterinarian-id") Integer id,
            @RequestParam(name = "begin-date", required = false) LocalDate beginDate,
            @RequestParam(name = "complete-date", required = false) LocalDate completeDate) {
        LocalDate effectiveBeginDate = ofNullable(beginDate).orElse(now());
        LocalDate effectiveCompleteDate = ofNullable(completeDate).orElse(effectiveBeginDate.plusMonths(1));
        return ResponseEntity.ok(availabilityService.calculateVeterinarianAvailabilitySlots(id, effectiveBeginDate,
                effectiveCompleteDate));
    }

}