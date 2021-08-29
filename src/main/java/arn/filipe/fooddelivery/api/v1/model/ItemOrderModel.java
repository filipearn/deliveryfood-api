package arn.filipe.fooddelivery.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "items")
@Getter
@Setter
public class ItemOrderModel extends RepresentationModel<ItemOrderModel> {

    @ApiModelProperty(example = "1")
    private Long productId;

    @ApiModelProperty(example = "Chorizzo")
    private String productName;

    @ApiModelProperty(example = "1")
    private Integer quantity;

    @ApiModelProperty(example = "59.90")
    private BigDecimal unitPrice;

    @ApiModelProperty(example = "59.90")
    private BigDecimal totalPrice;

    @ApiModelProperty(example = "Ao ponto menos")
    private String observation;
}
