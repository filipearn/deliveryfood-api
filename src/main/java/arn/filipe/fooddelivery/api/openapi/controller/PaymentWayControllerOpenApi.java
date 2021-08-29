package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.CityModel;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import arn.filipe.fooddelivery.api.model.input.CityInput;
import arn.filipe.fooddelivery.api.model.input.PaymentWayInput;
import arn.filipe.fooddelivery.api.openapi.model.PaymentWaysModelOpenApi;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
