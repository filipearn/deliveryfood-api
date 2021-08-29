package arn.filipe.fooddelivery.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @ApiModelProperty(example = "35400-000", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Conde Bobadela", required = true)
    @NotBlank
    private String street;

    @ApiModelProperty(example = "50", required = true)
    @NotBlank
    private String number;

    @ApiModelProperty(example = "A")
    private String complement;

    @ApiModelProperty(example = "Centro", required = true)
    @NotBlank
    private String district;

    @Valid
    @NotNull
    private CityIdInput city;
}
