package com.manager.animallist.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.manager.animallist.controller.AnimalController;
import com.manager.animallist.payload.AnimalDetailsResponse;

@Component
public class AnimalAssembler implements RepresentationModelAssembler<AnimalDetailsResponse, EntityModel<AnimalDetailsResponse>> {

    @Override
    public EntityModel<AnimalDetailsResponse> toModel(AnimalDetailsResponse animalDetailsResponse) {
        return EntityModel.of(animalDetailsResponse,
                linkTo(methodOn(AnimalController.class).getAnimalById(animalDetailsResponse.getId())).withSelfRel());
    }
}