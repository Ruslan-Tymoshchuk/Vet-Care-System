package com.manager.animallist.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEmailValidationResponse {

    private String response;
    
}