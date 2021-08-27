package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.api.model.input.StateIdInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "City", description = "Represents a city")
@Getter
@Setter
public class CityModel {

    //@ApiModelProperty(value = "City id", example = "1")
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Belo Horizonte")
    private String name;

    private StateModel state;
}
