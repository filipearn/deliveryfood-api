package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenModel {

    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @JsonView(RestaurantView.Summary.class)
    private String name;
}
