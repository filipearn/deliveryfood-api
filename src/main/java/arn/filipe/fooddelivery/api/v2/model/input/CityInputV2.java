package arn.filipe.fooddelivery.api.v2.model.input;

import arn.filipe.fooddelivery.api.v1.model.input.StateIdInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInputV2 {



    private String cityName;


    private Long stateId;

    private String stateDescription;

//    @Valid
//    @NotNull
//    private StateIdInput state;
}
