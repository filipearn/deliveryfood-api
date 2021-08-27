package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Purchase orders")
public interface OrderFlowControllerOpenApi {

    @ApiOperation("Change the purchase order status to CONFIRMED")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Purchase order changed with success"),
            @ApiResponse(code = 404, message = "Purchase order not found", response = ApiError.class),
            @ApiResponse(code = 400, message = "Purchase order status can't be changed")
    })
    void confirmation(@ApiParam(value = "Purchase order code", example = "9197a815-51b9-4fd1-9f74-6e0c55a683d7", required = true) String purchaseOrderCode);

    @ApiOperation("Change the purchase order status to DELIVERED")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Purchase order changed with success"),
            @ApiResponse(code = 404, message = "Purchase order not found", response = ApiError.class),
            @ApiResponse(code = 400, message = "Purchase order status can't be changed")
    })
    void delivery(@ApiParam(value = "Purchase order code", example = "9197a815-51b9-4fd1-9f74-6e0c55a683d7", required = true) String purchaseOrderCode);

    @ApiOperation("Change the purchase order status to CANCELLED")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Purchase order changed with success"),
            @ApiResponse(code = 404, message = "Purchase order not found", response = ApiError.class),
            @ApiResponse(code = 400, message = "Purchase order status can't be changed")
    })
    void cancellation(@ApiParam(value = "Purchase order code", example = "9197a815-51b9-4fd1-9f74-6e0c55a683d7", required = true) String purchaseOrderCode);
}
