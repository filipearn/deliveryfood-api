package arn.filipe.fooddelivery.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PermissionInput {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "Gerente", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "Permission description", required = true)
    @NotBlank
    private String description;
}
