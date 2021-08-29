package arn.filipe.fooddelivery.api.v1.openapi.model;

import arn.filipe.fooddelivery.api.v1.model.ProductModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProductsModel")
@Data
public class ProductsModelOpenApi {

    private ProductsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("ProductsEmbeddedModel")
    @Data
    public class ProductsEmbeddedModelOpenApi{
        private List<ProductModel> products;
    }
}
