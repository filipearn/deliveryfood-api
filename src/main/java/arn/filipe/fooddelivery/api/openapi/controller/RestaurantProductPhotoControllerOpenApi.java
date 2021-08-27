package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.PhotoProductModel;
import arn.filipe.fooddelivery.api.model.input.PhotoProductInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

@Api(tags = "Products")
public interface RestaurantProductPhotoControllerOpenApi {

    @ApiOperation(value = "Find a restaurant's product photo by product id",
                produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant or product id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant ou product not found", response = ApiError.class)
    })
    PhotoProductModel find(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                           @ApiParam(value = "Product id", example = "1", required = true)Long productId) throws Exception;

    @ApiOperation(value = "Find the restaurant's product photo", hidden = true)
    ResponseEntity<?> servePhoto(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                                 @ApiParam(value = "Product id", example = "1", required = true) Long productId,
                                         String acceptHeader) throws Exception;

    @ApiOperation(value = "Register a new restaurant's product photo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product photo updated with success"),
            @ApiResponse(code = 400, message = "Invalid restaurant or product id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = ApiError.class)
    })
    PhotoProductModel updatePhoto( @ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                                   @ApiParam(value = "Product id", example = "1", required = true)Long productId,
                                   @ApiParam(name = "body", value = "Representation of a product photo with new data", required = true)
                                           PhotoProductInput productPhotoInput) throws IOException;

    @ApiOperation(value = "Delete a restaurant's product photo by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant or product id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = ApiError.class)
    })
    void deletePhoto(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                     @ApiParam(value = "Product id", example = "1", required = true)Long productId);

}
