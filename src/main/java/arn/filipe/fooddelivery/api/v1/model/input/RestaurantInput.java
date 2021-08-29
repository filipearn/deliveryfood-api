package arn.filipe.fooddelivery.api.v1.model.input;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class RestaurantInput {

    @ApiModelProperty(example = "Komb espeteria", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "9.99", required = true)
    @NotNull
	@PositiveOrZero
    private BigDecimal freightRate;

    @Valid
	@NotNull
    private KitchenIdInput kitchen;

    @Valid
    @NotNull
    private AddressInput address;
}
