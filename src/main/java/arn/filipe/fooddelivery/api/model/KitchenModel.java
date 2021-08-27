package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenModel {

    @ApiModelProperty(example = "1")
    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestaurantView.Summary.class)
    private String name;
}
