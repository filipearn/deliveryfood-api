package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.UserModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurants")
public interface RestaurantUserControllerOpenApi {

    @ApiOperation("List the responsible users by restaurant id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    CollectionModel<UserModel> listAll(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId);

    @ApiOperation("Associate a user with a restaurant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Associated with success"),
            @ApiResponse(code = 400, message = "Invalid resource id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    ResponseEntity<Void> associateUser(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                                       @ApiParam(value = "User id", example = "1", required = true) Long userId);

    @ApiOperation("Disassociate a user with a restaurant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Disassociated with success"),
            @ApiResponse(code = 400, message = "Invalid resource id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    ResponseEntity<Void> disassociateUser(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                          @ApiParam(value = "User id", example = "1", required = true) Long userId);
}
