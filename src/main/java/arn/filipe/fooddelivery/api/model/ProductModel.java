package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.domain.model.Restaurant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Chorizzo")
    private String name;

    @ApiModelProperty(example = "É extraída da parte dianteira do contrafilé bovino")
    private String description;

    @ApiModelProperty(example = "59.90")
    private BigDecimal price;

    @ApiModelProperty(example = "true")
    private Boolean active;
}
