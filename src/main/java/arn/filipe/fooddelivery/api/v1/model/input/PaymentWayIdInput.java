package arn.filipe.fooddelivery.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentWayIdInput {

    @ApiModelProperty(example = "1", required = true)
    private Long id;
}
