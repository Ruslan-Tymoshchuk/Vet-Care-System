package com.manager.animallist.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.manager.animallist.controller.PetController;
import com.manager.animallist.payload.PetDetailsResponse;

@Component
public class PetAssembler implements RepresentationModelAssembler<PetDetailsResponse, EntityModel<PetDetailsResponse>> {

    @Override
    public EntityModel<PetDetailsResponse> toModel(PetDetailsResponse animalDetailsResponse) {
        return EntityModel.of(animalDetailsResponse,
                linkTo(methodOn(PetController.class).getAnimalById(animalDetailsResponse.getId())).withSelfRel());
    }
}