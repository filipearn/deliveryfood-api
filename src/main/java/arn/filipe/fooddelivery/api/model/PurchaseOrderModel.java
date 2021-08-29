package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "purchaseOrders")
@Getter
@Setter
public class PurchaseOrderModel extends RepresentationModel<PurchaseOrderModel> {

    @ApiModelProperty(example = "9197a815-51b9-4fd1-9f74-6e0c55a683d7")
    private String code;

    @ApiModelProperty(example = "350.20")
    private BigDecimal subTotal;

    @ApiModelProperty(example = "9.99")
    private BigDecimal freightRate;

    @ApiModelProperty(example = "360.19")
    private BigDecimal totalValue;

    private AddressModel address;

    @ApiModelProperty(example = "2021-08-26T20:27:16Z")
    private OffsetDateTime registrationDate;

    @ApiModelProperty(example = "2021-08-26T20:35:16Z")
    private OffsetDateTime confirmationDate;

    @ApiModelProperty(example = "2021-08-26T20:36:16Z")
    private OffsetDateTime cancellationDate;

    @ApiModelProperty(example = "2021-08-26T21:27:16Z")
    private OffsetDateTime deliveryDate;

    private UserModel client;

    private List<ItemOrderModel> items;

    private RestaurantSummaryModel restaurant;

    private PaymentWayModel paymentWay;

    @ApiModelProperty(example = "CONFIRMED")
    private String status;
}
