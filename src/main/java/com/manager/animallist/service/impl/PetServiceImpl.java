package com.manager.animallist.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.manager.animallist.domain.Pet;
import com.manager.animallist.payload.PetDetailsRequest;
import com.manager.animallist.payload.PetDetailsResponse;
import com.manager.animallist.payload.mapstruct.PetMapper;
import com.manager.animallist.repository.PetRepository;
import com.manager.animallist.service.PetService;
import com.manager.animallist.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {

    private final UserService userService;
    private final PetMapper animalMapper;
    private final PetRepository animalRepository;

    @Override
    public List<PetDetailsResponse> findAllAnimals() {
        return animalRepository.findAll().stream().map(animalMapper::petToPetDetailsResponse).toList();
    }

    @Override
    public List<PetDetailsResponse> findAllAnimalsByUser(String userEmail) {
        return animalRepository.findByUser(userEmail).stream().map(animalMapper::petToPetDetailsResponse)
                .toList();
    }

    @Override
    @Transactional
    public PetDetailsResponse createAnimal(PetDetailsRequest animalDetails, String userEmail) {
        Pet pet = animalRepository.save(animalMapper.petDetailsToPet(animalDetails));
        userService.assignUser(pet, userEmail);
        return animalMapper.petToPetDetailsResponse(pet);
    }

    @Override
    public PetDetailsResponse getAnimalById(Integer id) {
        return animalMapper.petToPetDetailsResponse(animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find animal with id: " + id)));
    }

    @Override
    @Transactional
    public PetDetailsResponse updateAnimal(PetDetailsRequest animalDetails, Integer animalId, String userEmail) {
        Pet animal = animalMapper.petDetailsToPet(animalDetails, animalId);
        userService.assignUser(animal, userEmail);
        return animalMapper.petToPetDetailsResponse(animalRepository.save(animal));
    }

    @Override
    @Transactional
    public void deleteAnimalById(Integer id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Could not find animal with id: " + id);
        }
    }
}