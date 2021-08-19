package arn.filipe.fooddelivery.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PermissionInput {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
