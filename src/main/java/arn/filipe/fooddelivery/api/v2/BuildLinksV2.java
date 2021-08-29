package arn.filipe.fooddelivery.api.v2;

import arn.filipe.fooddelivery.api.v2.controller.CityControllerV2;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuildLinksV2 {

    public Link linkToCity(Long cityId, String rel) {
        return linkTo(methodOn(CityControllerV2.class)
                .findById(cityId)).withRel(rel);

    }

    public Link linkToCity(String rel) {
        return linkTo(CityControllerV2.class).withRel(rel);
    }


}
