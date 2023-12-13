package com.manager.animallist.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
import com.manager.animallist.dto.AnimalDto;
import com.manager.animallist.service.AnimalService;

@RestController
@RequestMapping("/api/v1/animals")
public class AnimalRestController {

    private final AnimalService animalService;
    private final AnimalAssembler animalAssembler;

    public AnimalRestController(AnimalService animalService, AnimalAssembler animalAssembler) {
        this.animalService = animalService;
        this.animalAssembler = animalAssembler;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<AnimalDto>> getAllAnimals() {
        List<EntityModel<AnimalDto>> animals = animalService.findAllAnimals().stream().map(animalAssembler::toModel)
                .toList();
        return CollectionModel.of(animals, linkTo(AnimalRestController.class).withSelfRel());
    }

    @GetMapping("/by_user")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<AnimalDto>> getAnimalsByUser(@AuthenticationPrincipal(expression = "id") Integer id) {
        List<EntityModel<AnimalDto>> animals = animalService.findAllAnimalsByUserId(id).stream()
                .map(animalAssembler::toModel).toList();
        return CollectionModel.of(animals, linkTo(AnimalRestController.class).withSelfRel());
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<AnimalDto> getAnimalById(@PathVariable("id") Integer id) {
        return animalAssembler.toModel(animalService.getAnimalById(id));
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<AnimalDto> saveNewAnimal(@AuthenticationPrincipal(expression = "id") Integer id,
            @RequestBody AnimalDto animalDto) {
        animalDto.setUserId(id);
        return animalAssembler.toModel(animalService.createAnimal(animalDto));
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnimal(@AuthenticationPrincipal(expression = "id") Integer id, @RequestBody AnimalDto animalDto) {
        animalDto.setUserId(id);
        animalService.updateAnimal(animalDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimalById(@PathVariable("id") Integer id) {
        animalService.deleteAnimalById(id);
    }
}