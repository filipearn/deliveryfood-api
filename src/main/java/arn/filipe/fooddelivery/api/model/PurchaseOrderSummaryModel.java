package arn.filipe.fooddelivery.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PurchaseOrderSummaryModel {
    private Long id;

    private BigDecimal subTotal;

    private BigDecimal freightRate;

    private BigDecimal totalValue;

    private OffsetDateTime registrationDate;

    private UserModel client;

    private RestaurantSummaryModel restaurant;

    private String status;
}
