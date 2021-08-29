package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.UserModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("UsersModel")
@Data
public class UsersModelOpenApi {

    private UsersEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("UsersEmbeddedModel")
    @Data
    public class UsersEmbeddedModelOpenApi{
        private List<UserModel> users;
    }
}
