package com.system.vetcare.payload;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private final String email;
    private final String password;
    
}