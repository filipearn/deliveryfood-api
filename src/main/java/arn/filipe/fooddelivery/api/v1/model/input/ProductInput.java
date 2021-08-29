package arn.filipe.fooddelivery.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {

    @ApiModelProperty(example = "Chorizzo", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "É extraída da parte dianteira do contrafilé bovino", required = true)
    @NotBlank
    private String description;

    @ApiModelProperty(example = "59.90", required = true)
    @PositiveOrZero
    private BigDecimal price;

    @ApiModelProperty(example = "true", required = true)
    @NotNull
    private Boolean active;
}
