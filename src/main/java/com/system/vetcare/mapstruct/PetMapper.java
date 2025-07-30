package com.system.vetcare.mapstruct;

import org.mapstruct.Mapper;

import com.system.vetcare.domain.Pet;
import com.system.vetcare.payload.PetDetailsRequest;
import com.system.vetcare.payload.PetDetailsResponse;

@Mapper(componentModel = "spring")
public interface PetMapper {
    
    PetDetailsResponse petToPetDetailsResponse(Pet pet);

    Pet petDetailsToPet(PetDetailsRequest petDetailsRequest);
    
    Pet petDetailsToPet(PetDetailsRequest petDetailsRequest, Integer id);
    
}