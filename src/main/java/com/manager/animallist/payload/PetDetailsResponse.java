package com.manager.animallist.payload;

import lombok.Data;

@Data
public class PetDetailsResponse {

    private Integer id;
    private String birthDate;
    private String gender;
    private String nickName;
   
}