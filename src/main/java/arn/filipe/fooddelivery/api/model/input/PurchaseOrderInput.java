package arn.filipe.fooddelivery.api.model.input;

import arn.filipe.fooddelivery.api.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderInput {

    @Valid
    @NotNull
    private RestaurantIdInput restaurant;

    @Valid
    @NotNull
    private AddressInput deliveryAddress;

    @Valid
    @NotNull
    private PaymentWayIdInput paymentWay;

    @Valid
    @Size(min =1)
    @NotNull
    private List<ItemOrderInput> items;
}
