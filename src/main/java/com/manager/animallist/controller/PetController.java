package com.manager.animallist.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.*;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.manager.animallist.controller.assembler.PetAssembler;
import com.manager.animallist.payload.PetDetailsRequest;
import com.manager.animallist.payload.PetDetailsResponse;
import com.manager.animallist.service.PetService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/animals")
public class PetController {

    private final PetService animalService;
    private final PetAssembler animalAssembler;

    @Secured({ "ADMIN" })
    @GetMapping
    public CollectionModel<EntityModel<PetDetailsResponse>> getAllAnimals() {
        List<EntityModel<PetDetailsResponse>> animals = animalService.findAllAnimals().stream()
                .map(animalAssembler::toModel).toList();
        return CollectionModel.of(animals, linkTo(PetController.class).withSelfRel());
    }

    @Secured({ "USER" })
    @GetMapping("/my_animals")
    public List<EntityModel<PetDetailsResponse>> getAnimalsByUser(
            @AuthenticationPrincipal(expression = "username") String userEmail) { 
        return animalService.findAllAnimalsByUser(userEmail).stream()
                .map(animalAssembler::toModel).toList();
    }

    @Secured({ "ADMIN", "USER" })
    @GetMapping("/{id}")
    public EntityModel<PetDetailsResponse> getAnimalById(@PathVariable("id") Integer id) {
        return animalAssembler.toModel(animalService.getAnimalById(id));
    }

    @Secured({ "USER" })
    @PostMapping
    @ResponseStatus(CREATED)
    public EntityModel<PetDetailsResponse> saveNewAnimal(@RequestBody PetDetailsRequest animalDetailsRequest,
            @AuthenticationPrincipal(expression = "username") String userEmail) {
        return animalAssembler.toModel(animalService.createAnimal(animalDetailsRequest, userEmail));
    }

    @Secured({ "USER" })
    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateAnimal(@PathVariable("id") Integer animalId,
            @RequestBody PetDetailsRequest animalDetailsRequest,
            @AuthenticationPrincipal(expression = "username") String userEmail) {
        animalService.updateAnimal(animalDetailsRequest, animalId, userEmail);
    }

    @Secured({ "ADMIN", "USER" })
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAnimalById(@PathVariable("id") Integer id) {
        animalService.deleteAnimalById(id);
    }
}