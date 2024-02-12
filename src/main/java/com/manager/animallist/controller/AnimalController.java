package com.manager.animallist.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
import com.manager.animallist.controller.assembler.AnimalAssembler;
import com.manager.animallist.payload.AnimalDetailsRequest;
import com.manager.animallist.payload.AnimalDetailsResponse;
import com.manager.animallist.service.AnimalService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalAssembler animalAssembler;

    @Secured({ "ADMIN" })
    @GetMapping
    public CollectionModel<EntityModel<AnimalDetailsResponse>> getAllAnimals() {
        List<EntityModel<AnimalDetailsResponse>> animals = animalService.findAllAnimals().stream()
                .map(animalAssembler::toModel).toList();
        return CollectionModel.of(animals, linkTo(AnimalController.class).withSelfRel());
    }

    @Secured({ "USER" })
    @GetMapping("/my_animals")
    public List<EntityModel<AnimalDetailsResponse>> getAnimalsByUser(
            @AuthenticationPrincipal(expression = "username") String userEmail) { 
        return animalService.findAllAnimalsByUser(userEmail).stream()
                .map(animalAssembler::toModel).toList();
    }

    @Secured({ "ADMIN", "USER" })
    @GetMapping("/{id}")
    public EntityModel<AnimalDetailsResponse> getAnimalById(@PathVariable("id") Integer id) {
        return animalAssembler.toModel(animalService.getAnimalById(id));
    }

    @Secured({ "ADMIN", "USER" })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<AnimalDetailsResponse> saveNewAnimal(@RequestBody AnimalDetailsRequest animalDetailsRequest,
            @AuthenticationPrincipal(expression = "username") String userEmail) {
        return animalAssembler.toModel(animalService.createAnimal(animalDetailsRequest, userEmail));
    }

    @Secured({ "ADMIN", "USER" })
    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnimal(@AuthenticationPrincipal(expression = "username") String userEmail,
            @PathVariable("id") Integer animalId, @RequestBody AnimalDetailsRequest animalDetailsRequest) {
        animalService.updateAnimal(animalDetailsRequest, animalId, userEmail);
    }

    @Secured({ "ADMIN", "USER" })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimalById(@PathVariable("id") Integer id) {
        animalService.deleteAnimalById(id);
    }
}