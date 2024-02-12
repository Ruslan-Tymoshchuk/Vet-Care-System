package com.manager.animallist.payload.mapstruct;

import org.mapstruct.Mapper;
import com.manager.animallist.domain.Animal;
import com.manager.animallist.payload.AnimalDetailsRequest;
import com.manager.animallist.payload.AnimalDetailsResponse;

@Mapper(componentModel = "spring")
public interface AnimalMapper {
    
    AnimalDetailsResponse animalToAnimalDetailsResponse(Animal animal);

    Animal animalDetailsToAnimal(AnimalDetailsRequest animalDetails);
    
    Animal animalDetailsToAnimal(AnimalDetailsRequest animalDetails, Integer id);
    
}