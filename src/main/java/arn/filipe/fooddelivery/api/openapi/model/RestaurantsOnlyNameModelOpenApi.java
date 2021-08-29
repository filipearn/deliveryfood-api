package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.RestaurantOnlyNameModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel(" RestaurantsOnlyNameModel")
@Data
public class  RestaurantsOnlyNameModelOpenApi {

    private  RestaurantsOnlyNameEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel(" RestaurantsOnlyNameEmbeddedModel")
    @Data
    public class  RestaurantsOnlyNameEmbeddedModelOpenApi{
        private List<RestaurantOnlyNameModel>  RestaurantsOnlyName;
    }
}

