package com.manager.animallist.payload.mapstruct;

import org.mapstruct.Mapper;
import com.manager.animallist.domain.Pet;
import com.manager.animallist.payload.AnimalDetailsRequest;
import com.manager.animallist.payload.AnimalDetailsResponse;

@Mapper(componentModel = "spring")
public interface PetMapper {
    
    AnimalDetailsResponse animalToAnimalDetailsResponse(Pet animal);

    Pet animalDetailsToAnimal(AnimalDetailsRequest animalDetails);
    
    Pet animalDetailsToAnimal(AnimalDetailsRequest animalDetails, Integer id);
    
}