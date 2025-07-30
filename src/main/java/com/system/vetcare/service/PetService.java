package com.system.vetcare.service;

import java.util.List;

import com.system.vetcare.payload.PetDetailsRequest;
import com.system.vetcare.payload.PetDetailsResponse;

public interface PetService {

    List<PetDetailsResponse> findAllAnimals();
    
    List<PetDetailsResponse> findAllAnimalsByUser(String userEmail);
    
    PetDetailsResponse createAnimal(PetDetailsRequest newAnimal, String userEmail);
    
    PetDetailsResponse getAnimalById(Integer id);
    
    PetDetailsResponse updateAnimal(PetDetailsRequest animalDetailsRequest, Integer animalId,  String userEmail);
    
    void deleteAnimalById(Integer id);

}