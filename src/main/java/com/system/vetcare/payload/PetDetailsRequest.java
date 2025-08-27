package com.system.vetcare.payload;

public record PetDetailsRequest(
        Integer id,
        Integer ownerId,
        Integer veterinarianId,
        String birthDate, 
        String gender, 
        String nickName) {
}