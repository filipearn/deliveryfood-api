package arn.filipe.fooddelivery.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordInput {

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String actualPassword;

    @ApiModelProperty(example = "321", required = true)
    @NotBlank
    private String newPassword;
}
