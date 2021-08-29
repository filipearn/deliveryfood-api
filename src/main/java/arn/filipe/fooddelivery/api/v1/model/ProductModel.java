package arn.filipe.fooddelivery.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "products")
@Getter
@Setter
public class ProductModel extends RepresentationModel<ProductModel> {

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
