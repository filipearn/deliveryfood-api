package arn.filipe.fooddelivery.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissions")
@Getter
@Setter
public class PermissionModel extends RepresentationModel<PermissionModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerente")
    private String name;

    @ApiModelProperty(example = "Permissão para listagem e exclusão de itens no projeto X")
    private String description;
}
