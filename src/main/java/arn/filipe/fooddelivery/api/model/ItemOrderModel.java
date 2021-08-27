package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemOrderModel {

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
