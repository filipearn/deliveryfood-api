package arn.filipe.fooddelivery.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantOnlyNameModel extends RepresentationModel<RestaurantOnlyNameModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Komb espeteria")
    private String name;

}
