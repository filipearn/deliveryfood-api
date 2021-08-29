package arn.filipe.fooddelivery.api.v1.model;


import arn.filipe.fooddelivery.api.v1.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RestaurantModel extends RepresentationModel<RestaurantModel> {

    @ApiModelProperty(example = "1")
    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
    private Long id;

    @ApiModelProperty(example = "Komb espeteria")
    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
    private String name;

    @ApiModelProperty(example = "9.99")
    @JsonView(RestaurantView.Summary.class)
    private BigDecimal freightRate;

    @JsonView(RestaurantView.Summary.class)
    private KitchenModel kitchen;

    @ApiModelProperty(example = "true")
    private Boolean active;

    @ApiModelProperty(example = "true")
    private Boolean opened;

    private AddressModel address;

    private List<PaymentWayModel> paymentWays;

}
