package com.manager.animallist.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.manager.animallist.domain.Animal;
import com.manager.animallist.exception.AnimalNickNameAlredyExistsException;
import com.manager.animallist.exception.ResourceNotFoundException;
import com.manager.animallist.payload.AnimalDetailsRequest;
import com.manager.animallist.payload.AnimalDetailsResponse;
import com.manager.animallist.payload.mapstruct.AnimalMapper;
import com.manager.animallist.repository.AnimalRepository;
import com.manager.animallist.service.AnimalService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public List<AnimalDetailsResponse> findAllAnimals() {
        return animalRepository.findAll().stream().map(animalMapper::animalToAnimalDetailsResponse).toList();
    }

    @Override
    public List<AnimalDetailsResponse> findAllAnimalsByUser(String userEmail) {
        return animalRepository.findByUser(userEmail).stream().map(animalMapper::animalToAnimalDetailsResponse).toList();
    }

    @Override
    @Transactional
    public AnimalDetailsResponse createAnimal(AnimalDetailsRequest animalDetails, String userEmail) {
        if (!animalRepository.existsByNickName(animalDetails.getNickName())) {
            Animal animal = animalRepository.save(animalMapper.animalDetailsToAnimal(animalDetails));
            return animalMapper.animalToAnimalDetailsResponse(animal);
        } else {
            throw new AnimalNickNameAlredyExistsException("Animal with that nickname already exists");
        }
    }

    @Override
    public AnimalDetailsResponse getAnimalById(Integer id) {
        return animalMapper.animalToAnimalDetailsResponse(animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find animal with id: ", id)));
    }

    @Override
    @Transactional
    public void updateAnimal(AnimalDetailsRequest animalDetails, Integer animalId, String userEmail) {
        if (!animalRepository.existsByNickName(animalDetails.getNickName())) {
            Animal animal = animalRepository.save(animalMapper.animalDetailsToAnimal(animalDetails));
            animalMapper.animalToAnimalDetailsResponse(animal);
        } else {
            throw new AnimalNickNameAlredyExistsException("Animal with that nickname already exists");
        }
    }

    @Override
    @Transactional
    public void deleteAnimalById(Integer id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Could not find animal with id: ", id);
        }
    }
}