package com.system.vetcare.payload;

import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class AuthenticationResponse {

    private final String email;
    private final Set<String> roles;
    
}