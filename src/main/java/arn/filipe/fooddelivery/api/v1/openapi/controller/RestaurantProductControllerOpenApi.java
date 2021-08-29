package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.ProductModel;
import arn.filipe.fooddelivery.api.v1.model.input.ProductInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Products")
public interface RestaurantProductControllerOpenApi {

    @ApiOperation(value = "List products by restaurant id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    CollectionModel<ProductModel> listAll(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                                          @ApiParam(value = "Indicated if must include inactive products on result list",
                                                  example = "false", defaultValue = "false") Boolean includeInactivated);

    @ApiOperation(value = "Find restaurant's product by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant or product id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant ou product not found", response = ApiError.class)
    })
    ProductModel findById(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                          @ApiParam(value = "Product id", example = "1", required = true)Long productId);

    @ApiOperation(value = "Register a new restaurant's product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Invalid restaurant or product id"),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = ApiError.class)
    })
    ProductModel save(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                      @ApiParam(name = "body", value = "Representation of a product with new data", required = true) ProductInput productInput);

    @ApiOperation(value = "Register a new restaurant's product")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product updated with success"),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = ApiError.class)
    })
    ProductModel update(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                        @ApiParam(value = "Product id", example = "1", required = true) Long productId,
                         @ApiParam(name = "body", value = "Representation of a restaurant with new data", required = true) ProductInput productInput);

    @ApiOperation(value = "Delete a restaurant's product by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant or product id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = ApiError.class)
    })
    void delete(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                 @ApiParam(value = "Product id", example = "1", required = true)Long productId);


}
