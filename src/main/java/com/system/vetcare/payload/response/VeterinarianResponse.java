package com.system.vetcare.payload.response;

public record VeterinarianResponse(
        Integer id,
        String firstName,
        String lastName,
        String phone,
        String seniorityLevel,
        String totalExperience) {
}