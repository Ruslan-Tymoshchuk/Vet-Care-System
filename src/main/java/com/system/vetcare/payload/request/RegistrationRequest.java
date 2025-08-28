package com.system.vetcare.payload.request;

import java.util.List;

public record RegistrationRequest(
        String firstName, 
        String lastName, 
        String email, 
        String password,
        String legalCertificateId, 
        List<Integer> authorityIds) {
}