package com.manager.animallist.service;

import java.util.List;
import com.manager.animallist.payload.AnimalDetailsRequest;
import com.manager.animallist.payload.AnimalDetailsResponse;

public interface AnimalService {

    List<AnimalDetailsResponse> findAllAnimals();
    
    List<AnimalDetailsResponse> findAllAnimalsByUser(String userEmail);
    
    AnimalDetailsResponse createAnimal(AnimalDetailsRequest newAnimal, String userEmail);
    
    AnimalDetailsResponse getAnimalById(Integer id);
    
    AnimalDetailsResponse updateAnimal(AnimalDetailsRequest animalDetailsRequest, Integer animalId,  String userEmail);
    
    void deleteAnimalById(Integer id);

}