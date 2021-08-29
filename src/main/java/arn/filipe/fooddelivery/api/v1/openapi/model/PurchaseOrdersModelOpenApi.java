package arn.filipe.fooddelivery.api.v1.openapi.model;

import arn.filipe.fooddelivery.api.v1.model.PurchaseOrderModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PurchaseOrdersModel")
@Data
public class PurchaseOrdersModelOpenApi {

    private PurchaseOrdersEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("PurchaseOrdersEmbeddedModel")
    @Data
    public class PurchaseOrdersEmbeddedModelOpenApi{
        private List<PurchaseOrderModel> purchaseOrders;
    }
}
