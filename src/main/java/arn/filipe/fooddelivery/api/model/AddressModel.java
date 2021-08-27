package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressModel {

    @ApiModelProperty(example = "35400-000")
    private String cep;

    @ApiModelProperty(example = "Rua Conde Bobadela")
    private String street;

    @ApiModelProperty(example = "50")
    private String number;

    @ApiModelProperty(example = "A")
    private String complement;

    @ApiModelProperty(example = "Centro")
    private String district;

    private CitySummaryModel city;
}
