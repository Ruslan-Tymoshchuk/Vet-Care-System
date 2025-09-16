package com.system.vetcare.payload.response;

public record PetDetailsResponse(
        Integer id, 
        String birthDate, 
        String gender, 
        String nickName) {
}