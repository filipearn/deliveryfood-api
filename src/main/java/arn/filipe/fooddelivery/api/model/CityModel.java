package arn.filipe.fooddelivery.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CityModel {

    private Long id;
    private String name;
    private Long stateId;
}
