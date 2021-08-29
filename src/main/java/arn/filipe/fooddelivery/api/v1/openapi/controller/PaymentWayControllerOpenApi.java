package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.PaymentWayModel;
import arn.filipe.fooddelivery.api.v1.model.input.PaymentWayInput;
import arn.filipe.fooddelivery.api.v1.openapi.model.PaymentWaysModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Payment ways")
public interface PaymentWayControllerOpenApi {

    @ApiOperation(value = "List the payment ways", response = PaymentWaysModelOpenApi.class)
    ResponseEntity<CollectionModel<PaymentWayModel>> listAll(ServletWebRequest request);

    @ApiOperation("Find a payment way by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid payment way id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Payment way not found", response = ApiError.class)
    })
    ResponseEntity<PaymentWayModel> findById(@ApiParam(value = "Payment way id", example = "1", required = true) Long id, ServletWebRequest request);

    @ApiOperation("Register a new payment way")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Payment way created with success")
    })
    PaymentWayModel save(@ApiParam(name = "body", value = "Representation of a new payment way") PaymentWayInput paymentWayInput);

    @ApiOperation("Update a payment way by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated with success"),
            @ApiResponse(code = 404, message = "Payment way not found", response = ApiError.class)
    })
    PaymentWayModel update(@ApiParam(value = "Payment way id", example = "1", required = true) Long id,
                            @ApiParam(name = "body", value = "Representation of a payment way with new data", required = true) PaymentWayInput paymentWayInput);

    @ApiOperation("Delete a payment way by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid payment way id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Payment way not found", response = ApiError.class)
    })
    void delete(@ApiParam(value = "Payment way id", example = "1", required = true) Long id);
}
