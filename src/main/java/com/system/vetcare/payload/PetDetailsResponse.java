package com.system.vetcare.payload;

public record PetDetailsResponse(
        Integer id, 
        String birthDate, 
        String gender, 
        String nickName) {
}