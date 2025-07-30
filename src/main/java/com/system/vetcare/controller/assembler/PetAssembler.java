package com.system.vetcare.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.system.vetcare.controller.PetController;
import com.system.vetcare.payload.PetDetailsResponse;

@Component
public class PetAssembler implements RepresentationModelAssembler<PetDetailsResponse, EntityModel<PetDetailsResponse>> {

    @Override
    public EntityModel<PetDetailsResponse> toModel(PetDetailsResponse animalDetailsResponse) {
        return EntityModel.of(animalDetailsResponse,
                linkTo(methodOn(PetController.class).getAnimalById(animalDetailsResponse.getId())).withSelfRel());
    }
}