package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.domain.enums.OrderStatus;
import arn.filipe.fooddelivery.domain.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderModel {

    private Long id;

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
