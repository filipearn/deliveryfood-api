package arn.filipe.fooddelivery.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "purchaseOrders")
@Getter
@Setter
public class PurchaseOrderSummaryModel extends RepresentationModel<PurchaseOrderSummaryModel> {
    private String code;

    private BigDecimal subTotal;

    private BigDecimal freightRate;

    private BigDecimal totalValue;

    private OffsetDateTime registrationDate;

    private UserModel client;

    private RestaurantSummaryModel restaurant;

    private String status;
}
