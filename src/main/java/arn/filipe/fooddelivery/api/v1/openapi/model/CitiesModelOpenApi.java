package arn.filipe.fooddelivery.api.v1.openapi.model;

import arn.filipe.fooddelivery.api.v1.model.CityModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CitiesModel")
@Data
public class CitiesModelOpenApi {

    private CitiesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CitiesEmbeddedModel")
    @Data
    public class CitiesEmbeddedModelOpenApi{
        private List<CityModel> cities;
    }
}
