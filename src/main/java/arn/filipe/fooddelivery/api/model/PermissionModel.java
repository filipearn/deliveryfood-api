package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerente")
    private String name;

    @ApiModelProperty(example = "Permissão para listagem e exclusão de itens no projeto X")
    private String description;
}
