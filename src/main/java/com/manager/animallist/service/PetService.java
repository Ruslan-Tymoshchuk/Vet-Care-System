package com.manager.animallist.service;

import java.util.List;
import com.manager.animallist.payload.PetDetailsRequest;
import com.manager.animallist.payload.PetDetailsResponse;

public interface PetService {

    List<PetDetailsResponse> findAllAnimals();
    
    List<PetDetailsResponse> findAllAnimalsByUser(String userEmail);
    
    PetDetailsResponse createAnimal(PetDetailsRequest newAnimal, String userEmail);
    
    PetDetailsResponse getAnimalById(Integer id);
    
    PetDetailsResponse updateAnimal(PetDetailsRequest animalDetailsRequest, Integer animalId,  String userEmail);
    
    void deleteAnimalById(Integer id);

}