package com.system.vetcare.controller;

import static com.system.vetcare.controller.constants.AuthorityTitle.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.payload.response.VeterinarianResponse;
import com.system.vetcare.service.VeterinarianService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VeterinarianController {

    public static final String URL_VETERINARIANS = "/api/v1/veterinarians";

    private final VeterinarianService veterinarianService;

    @Secured({ MANAGER, OWNER })
    @GetMapping(URL_VETERINARIANS)
    public ResponseEntity<List<VeterinarianResponse>> findAll() {
        return ResponseEntity
                 .ok(veterinarianService.findAll());
    }

}