package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.CityModel;
import arn.filipe.fooddelivery.api.model.StateModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("StatesModel")
@Getter
@Setter
public class StatesModelOpenApi {

    private StatesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("StatesEmbeddedModel")
    @Data
    public class StatesEmbeddedModelOpenApi {
        private List<StateModel> states;
    }
}
