package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantSummaryModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Komb espeteria")
    private String name;
}
