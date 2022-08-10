package com.rtim.zoo.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rtim.zoo.domain.Animal;
import com.rtim.zoo.domain.Person;

@Mapper(componentModel = "spring")
public interface MapstractMapper {
    
    @Mapping(target = "userId", source = "person.id")
    AnimalDto animalToAnimalDto(Animal animal);
    
    @Mapping(target = "person.id", source = "userId")
    Animal animalDtoToAnimal(AnimalDto animalDto);
    
    PersonDto personToPersonDto(Person person);
    
    Person personDtoToPerson(PersonDto personDto);

}