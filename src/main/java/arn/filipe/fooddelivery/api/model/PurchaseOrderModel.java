package arn.filipe.fooddelivery.api.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderModel {

    private String code;

    private BigDecimal subTotal;

    private BigDecimal freightRate;

    private BigDecimal totalValue;

    private AddressModel address;

    private OffsetDateTime registrationDate;

    private OffsetDateTime confirmationDate;

    private OffsetDateTime cancellationDate;

    private OffsetDateTime deliveryDate;

    private UserModel client;

    private List<ItemOrderModel> items;

    private RestaurantSummaryModel restaurant;

    private PaymentWayModel paymentWay;

    private String status;
}
