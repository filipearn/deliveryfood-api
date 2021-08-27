package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerente")
    private String name;
}
