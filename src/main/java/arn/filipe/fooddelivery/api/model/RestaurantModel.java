package arn.filipe.fooddelivery.api.model;


import arn.filipe.fooddelivery.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RestaurantModel {

    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
    private Long id;

    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
    private String name;

    @JsonView(RestaurantView.Summary.class)
    private BigDecimal freightRate;

    @JsonView(RestaurantView.Summary.class)
    private KitchenModel kitchen;

    private Boolean active;
    private Boolean opened;
    private AddressModel address;
    private List<PaymentWayModel> paymentWays;

}
