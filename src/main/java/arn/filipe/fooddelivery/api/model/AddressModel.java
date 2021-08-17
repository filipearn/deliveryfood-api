package arn.filipe.fooddelivery.api.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressModel {

    private String cep;
    private String street;
    private String number;
    private String complement;
    private String district;
    private CityModel city;
}
