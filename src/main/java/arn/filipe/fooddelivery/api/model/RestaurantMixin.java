package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.core.validation.FreighRate;
import arn.filipe.fooddelivery.core.validation.Groups;
import arn.filipe.fooddelivery.core.validation.ZeroValueIncludeDescription;
import arn.filipe.fooddelivery.domain.model.Address;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import arn.filipe.fooddelivery.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ZeroValueIncludeDescription(valueField = "FreighRate", descriptionField = "name", requiredDescription = "Free shipping")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantMixin {

    @JsonIgnore
    private Boolean active;

    //@JsonIgnore
    private LocalDateTime registrationDate;

    @JsonIgnore
    private LocalDateTime updateDate;

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private Kitchen kitchen;

    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private List<PaymentWay> paymentWay = new ArrayList<>();
}

