package com.manager.animallist.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.manager.animallist.domain.Pet;
import com.manager.animallist.payload.AnimalDetailsRequest;
import com.manager.animallist.payload.AnimalDetailsResponse;
import com.manager.animallist.payload.mapstruct.PetMapper;
import com.manager.animallist.repository.PetRepository;
import com.manager.animallist.service.AnimalService;
import com.manager.animallist.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements AnimalService {

    private final UserService userService;
    private final PetMapper animalMapper;
    private final PetRepository animalRepository;

    @Override
    public List<AnimalDetailsResponse> findAllAnimals() {
        return animalRepository.findAll().stream().map(animalMapper::animalToAnimalDetailsResponse).toList();
    }

    @Override
    public List<AnimalDetailsResponse> findAllAnimalsByUser(String userEmail) {
        return animalRepository.findByUser(userEmail).stream().map(animalMapper::animalToAnimalDetailsResponse)
                .toList();
    }

    @Override
    @Transactional
    public AnimalDetailsResponse createAnimal(AnimalDetailsRequest animalDetails, String userEmail) {
        Pet pet = animalRepository.save(animalMapper.animalDetailsToAnimal(animalDetails));
        userService.assignUser(pet, userEmail);
        return animalMapper.animalToAnimalDetailsResponse(pet);
    }

    @Override
    public AnimalDetailsResponse getAnimalById(Integer id) {
        return animalMapper.animalToAnimalDetailsResponse(animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find animal with id: " + id)));
    }

    @Override
    @Transactional
    public AnimalDetailsResponse updateAnimal(AnimalDetailsRequest animalDetails, Integer animalId, String userEmail) {
        Pet animal = animalMapper.animalDetailsToAnimal(animalDetails, animalId);
        userService.assignUser(animal, userEmail);
        return animalMapper.animalToAnimalDetailsResponse(animalRepository.save(animal));
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