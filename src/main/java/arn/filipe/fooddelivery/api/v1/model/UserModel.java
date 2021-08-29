package arn.filipe.fooddelivery.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "users")
@Getter
@Setter
public class UserModel extends RepresentationModel<UserModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Filipe")
    private String name;

    @ApiModelProperty(example = "filipearn@yahoo.com.br")
    private String email;

}
