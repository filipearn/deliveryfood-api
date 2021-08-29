package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.TeamModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("TeamsModel")
@Data
public class TeamsModelOpenApi {
    private TeamsModelOpenApi.TeamsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("TeamsEmbeddedModel")
    @Data
    public class TeamsEmbeddedModelOpenApi{
        private List<TeamModel> teams;
    }
}
