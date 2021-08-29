package arn.filipe.fooddelivery.api.v1.model;

import arn.filipe.fooddelivery.api.v1.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "kitchens")
@Getter
@Setter
public class KitchenModel extends RepresentationModel<KitchenModel> {

    @ApiModelProperty(example = "1")
    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestaurantView.Summary.class)
    private String name;
}
