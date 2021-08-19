package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.domain.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean active;
}
