package com.system.vetcare.controller;

import static com.system.vetcare.security.constants.AuthorityTitle.*;
import static org.springframework.http.HttpStatus.*;
import java.util.List;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.controller.assembler.PetAssembler;
import com.system.vetcare.payload.request.PetDetailsRequest;
import com.system.vetcare.payload.response.PetDetailsResponse;
import com.system.vetcare.service.PetService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PetController {

    public static final String URL_PETS = "/api/v1/pets";
    public static final String URL_PETS_ID = "/api/v1/pets/{id}";
    public static final String URL_PETS_OWNER_ID = "/api/v1/pets/owner/{owner-id}";
    public static final String URL_PETS_VETERINARIAN_ID = "/api/v1/pets/veterinarian/{veterinarian-id}";
    
    private final PetService petService;
    private final PetAssembler animalAssembler;

    @Secured({ MANAGER, OWNER, VETERINARIAN })
    @GetMapping(URL_PETS_ID)
    public ResponseEntity<EntityModel<PetDetailsResponse>> findById(@PathVariable("id") Integer id) {
        return ResponseEntity
                .ok(animalAssembler.toModel(petService.findById(id)));
    }
    
    @Secured({ MANAGER })
    @GetMapping(URL_PETS)
    public ResponseEntity<List<EntityModel<PetDetailsResponse>>> findAll() {
        return ResponseEntity
                .ok(petService.findAll().stream().map(animalAssembler::toModel).toList());
    }
    
    @Secured({ OWNER })
    @GetMapping(URL_PETS_OWNER_ID)
    public ResponseEntity<List<EntityModel<PetDetailsResponse>>> findAllByOwner(
            @PathVariable("owner-id") Integer ownerId) {
        return ResponseEntity
                .ok(petService.findAllByOwnerId(ownerId).stream().map(animalAssembler::toModel).toList());
    }
    
    @Secured({ VETERINARIAN })
    @GetMapping(URL_PETS_VETERINARIAN_ID)
    public ResponseEntity<List<EntityModel<PetDetailsResponse>>> findAllByVeterinarian(
            @PathVariable("veterinarian-id") Integer veterinarianId) {
        return ResponseEntity
                .ok(petService.findAllByVeterinarianId(veterinarianId).stream().map(animalAssembler::toModel).toList());
    }

    @Secured({ OWNER })
    @PostMapping(URL_PETS)
    public ResponseEntity<EntityModel<PetDetailsResponse>> save(@RequestBody PetDetailsRequest petDetailsRequest) {
        return ResponseEntity
                .status(CREATED)
                .body(animalAssembler.toModel(petService.save(petDetailsRequest)));
    }

    @Secured({ OWNER })
    @PatchMapping(URL_PETS_ID)
    public ResponseEntity<Void> updateAnimal(@PathVariable("id") Integer animalId,
            @RequestBody PetDetailsRequest petDetailsRequest,
            @AuthenticationPrincipal(expression = "username") String userEmail) {
        petService.updateAnimal(petDetailsRequest, animalId, userEmail);
        return ResponseEntity.noContent().build();
    }
    
}