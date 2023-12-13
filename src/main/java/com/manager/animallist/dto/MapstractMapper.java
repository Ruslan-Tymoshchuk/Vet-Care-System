package com.manager.animallist.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manager.animallist.domain.Animal;
import com.manager.animallist.domain.Person;

@Mapper(componentModel = "spring")
public interface MapstractMapper {
    
    @Mapping(target = "userId", source = "person.id")
    AnimalDto animalToAnimalDto(Animal animal);
    
    @Mapping(target = "person.id", source = "userId")
    Animal animalDtoToAnimal(AnimalDto animalDto);
    
    PersonDto personToPersonDto(Person person);
    
    Person personDtoToPerson(PersonDto personDto);

}