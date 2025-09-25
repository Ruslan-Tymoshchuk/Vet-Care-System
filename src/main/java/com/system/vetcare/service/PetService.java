package com.system.vetcare.service;

import java.util.List;
import com.system.vetcare.domain.Pet;
import com.system.vetcare.payload.request.PetDetailsRequest;
import com.system.vetcare.payload.response.PetDetailsResponse;

public interface PetService {

    PetDetailsResponse save(PetDetailsRequest petDetailsRequest);
    
    List<PetDetailsResponse> findAll();
    
    List<PetDetailsResponse> findAllByOwnerId(Integer ownerId);
    
    List<PetDetailsResponse> findAllByVeterinarianId(Integer veterinarianId);
    
    PetDetailsResponse findDetailsById(Integer id);
    
    Pet findById(Integer id);
    
    PetDetailsResponse updatePet(PetDetailsRequest petDetailsRequest);
    
    void deleteAnimalById(Integer id);

}