package com.system.vetcare.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.payload.AuthorityDetailsResponse;
import com.system.vetcare.service.AuthorityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthorityController {

    public static final String URL_AUTHORITIES_ALL = "/api/v1/authorities/all";
    
    private final AuthorityService authorityService;
    
    @GetMapping(URL_AUTHORITIES_ALL)
    public List<AuthorityDetailsResponse> findAll() {
        return authorityService.findAll();
    }
    
}