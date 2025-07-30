package com.system.vetcare.payload;

import lombok.Data;

@Data
public class PetDetailsRequest {

    private String birthDate;
    private String gender;
    private String nickName;
   
}