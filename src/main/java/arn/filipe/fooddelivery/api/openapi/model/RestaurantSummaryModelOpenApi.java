package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.KitchenModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("RestaurantSummaryModel")
@Getter
@Setter
public class RestaurantSummaryModelOpenApi {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Komb espeteria")
    private String name;

    @ApiModelProperty(example = "9.99")
    private BigDecimal freightRate;

    private KitchenModel kitchen;

}
