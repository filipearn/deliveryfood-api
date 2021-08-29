package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PaymentWaysModel")
@Data
public class PaymentWaysModelOpenApi {
    private PaymentWaysEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("PaymentWaysEmbeddedModel")
    @Data
    public class PaymentWaysEmbeddedModelOpenApi{
        private List<PaymentWayModel> paymentWays;
    }
}
