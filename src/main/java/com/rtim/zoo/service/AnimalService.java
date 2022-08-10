package com.rtim.zoo.service;

import java.util.List;
import com.rtim.zoo.dto.AnimalDto;

public interface AnimalService {

    List<AnimalDto> findAllAnimals();
    
    List<AnimalDto> findAllAnimalsByUserId(Integer userId);
    
    AnimalDto createAnimal(AnimalDto animalDto);
    
    AnimalDto getAnimalById(Integer id);
    
    void updateAnimal(AnimalDto animalDto);
    
    void deleteAnimalById(Integer id);

}