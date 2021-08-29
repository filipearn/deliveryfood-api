package arn.filipe.fooddelivery.api.v1.openapi.model;

import arn.filipe.fooddelivery.api.v1.model.CityModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;


@ApiModel("KitchensModel")
@Setter
@Getter
public class KitchensModelOpenApi {

    private KitchensEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("KitchensEmbeddedModel")
    @Data
    public class KitchensEmbeddedModelOpenApi{
        private List<CityModel> cities;
    }
}
