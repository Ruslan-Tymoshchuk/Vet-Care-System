package com.manager.animallist.payload;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private final String email;
    private final String password;
    
}