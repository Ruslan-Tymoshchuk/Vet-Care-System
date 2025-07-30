package com.manager.animallist.payload.mapstruct;

import org.mapstruct.Mapper;
import com.manager.animallist.domain.Pet;
import com.manager.animallist.payload.PetDetailsRequest;
import com.manager.animallist.payload.PetDetailsResponse;

@Mapper(componentModel = "spring")
public interface PetMapper {
    
    PetDetailsResponse petToPetDetailsResponse(Pet pet);

    Pet petDetailsToPet(PetDetailsRequest petDetailsRequest);
    
    Pet petDetailsToPet(PetDetailsRequest petDetailsRequest, Integer id);
    
}