package com.system.vetcare.service.impl;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.time.LocalDate.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.system.vetcare.domain.Pet;
import com.system.vetcare.domain.enums.EGender;
import com.system.vetcare.exception.EntityNotFoundException;
import com.system.vetcare.mapstruct.PetMapper;
import com.system.vetcare.payload.request.PetDetailsRequest;
import com.system.vetcare.payload.response.PetDetailsResponse;
import com.system.vetcare.repository.PetRepository;
import com.system.vetcare.service.OwnerService;
import com.system.vetcare.service.PetService;
import com.system.vetcare.service.VeterinarianService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String PET_WITH_ID_NOT_FOUND = "Pet with [id: %s] not found.";

    private final PetMapper petMapper;
    private final PetRepository petRepository;
    private final OwnerService ownerService;
    private final VeterinarianService veterinarianService;

    @Override
    public List<PetDetailsResponse> findAll() {
        return petRepository.findAll().stream().map(petMapper::petToPetDetailsResponse).toList();
    }

    @Override
    public List<PetDetailsResponse> findAllByOwnerId(Integer ownerId) {
        return petRepository.findByOwner(ownerId).stream().map(petMapper::petToPetDetailsResponse).toList();
    }
    
    @Override
    public List<PetDetailsResponse> findAllByVeterinarianId(Integer veterinarianId) {
        return petRepository.findByVeterinarian(veterinarianId).stream().map(petMapper::petToPetDetailsResponse)
                .toList();
    }

    @Override
    @Transactional
    public PetDetailsResponse save(PetDetailsRequest petDetailsRequest) {
        Pet pet = petRepository.save(toEntity(petDetailsRequest));
        return toDto(pet);
    }

    @Override
    public PetDetailsResponse findById(Integer id) {
        return petMapper.petToPetDetailsResponse(petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find animal with id: " + id)));
    }

    @Override
    @Transactional
    public PetDetailsResponse updateAnimal(PetDetailsRequest animalDetails, Integer animalId, String userEmail) {
        Pet animal = petMapper.petDetailsToPet(animalDetails, animalId);
        return petMapper.petToPetDetailsResponse(petRepository.save(animal));
    }

    @Override
    @Transactional
    public void deleteAnimalById(Integer id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Could not find animal with id: " + id);
        }
    }

    private Pet toEntity(PetDetailsRequest petDetailsRequest) {
        Integer id = petDetailsRequest.id();
        Pet pet = Pet
                    .builder()
                    .build();
        if (nonNull(id)) {
            pet = petRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(format(PET_WITH_ID_NOT_FOUND, id)));
        }
        pet.setBirthDate(parse(petDetailsRequest.birthDate(), DATE_FORMATTER));
        pet.setGender(EGender.valueOf(petDetailsRequest.gender()));
        pet.setNickName(petDetailsRequest.nickName());
        Integer ownerId = petDetailsRequest.ownerId();
        if (nonNull(ownerId)) {
            pet.setOwner(ownerService.findById(ownerId));
        }
        Integer veterinarianId = petDetailsRequest.veterinarianId();
        if (nonNull(veterinarianId)) {
            pet.setVeterinarian(veterinarianService.findById(veterinarianId));
        }
        return pet;
    }
    
    private PetDetailsResponse toDto(Pet pet) {
        return new PetDetailsResponse(pet.getId(), pet.getBirthDate().format(DATE_FORMATTER), pet.getGender().name(),
                pet.getNickName());
    }

}