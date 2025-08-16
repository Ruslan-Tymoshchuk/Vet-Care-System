package com.system.vetcare.payload;

import java.util.List;
import lombok.Data;

@Data
public class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String legalCertificateId;
    private final List<Integer> authorityIds;
    
}