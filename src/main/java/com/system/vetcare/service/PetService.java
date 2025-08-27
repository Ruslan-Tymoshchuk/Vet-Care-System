package com.system.vetcare.service;

import java.util.List;
import com.system.vetcare.payload.PetDetailsRequest;
import com.system.vetcare.payload.PetDetailsResponse;

public interface PetService {

    PetDetailsResponse save(PetDetailsRequest petDetailsRequest);
    
    List<PetDetailsResponse> findAll();
    
    List<PetDetailsResponse> findAllByOwnerId(Integer ownerId);
    
    PetDetailsResponse findById(Integer id);
    
    PetDetailsResponse updateAnimal(PetDetailsRequest animalDetailsRequest, Integer animalId,  String userEmail);
    
    void deleteAnimalById(Integer id);

   
  
}