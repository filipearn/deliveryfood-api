package arn.filipe.fooddelivery.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentWayIdInput {

    @ApiModelProperty(example = "1", required = true)
    private Long id;
}
