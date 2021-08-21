package arn.filipe.fooddelivery.api.model;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RestaurantModel {

    private Long id;
    private String name;
    private BigDecimal freightRate;
    private KitchenModel kitchen;
    private Boolean active;
    private Boolean opened;
    private AddressModel address;
    private List<PaymentWayModel> paymentWays;

}
