package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurants")
public interface RestaurantPaymentWayControllerOpenApi {

    @ApiOperation(value = "List payment ways by restaurant id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    CollectionModel<PaymentWayModel> listAll(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId);

    @ApiOperation("Associate a restaurant with a payment way")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid state id", response = ApiError.class),
            @ApiResponse(code = 404, message = "State not found", response = ApiError.class)
    })
    ResponseEntity<Void> associate(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                   @ApiParam(value = "Payment way id", example = "1", required = true) Long paymentWayId);

    @ApiOperation("Disassociate a restaurant with a payment way")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid state id", response = ApiError.class),
            @ApiResponse(code = 404, message = "State not found", response = ApiError.class)
    })
    ResponseEntity<Void> disassociate(@ApiParam(value = "Restaurant id", example = "1", required = true) Long restaurantId,
                      @ApiParam(value = "Payment way id", example = "1", required = true) Long paymentWayId);
}
