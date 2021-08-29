package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.PurchaseOrderModel;
import arn.filipe.fooddelivery.api.v1.model.input.PurchaseOrderInput;
import arn.filipe.fooddelivery.domain.filter.PurchaseOrderFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Purchase orders")
public interface PurchaseOrderControllerOpenApi {

    @ApiOperation("Find a purchase order using filter")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Properties name to filter the answer, separated by comma",
                    name = "fields", paramType = "query", type = "string")
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "Purchased order not found", response = ApiError.class)
    })
    PagedModel<PurchaseOrderModel> find(PurchaseOrderFilter filter, Pageable pageable);

    @ApiOperation("Find a purchase order by code")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Properties name to filter the answer, separated by comma",
                    name = "fields", paramType = "query", type = "string")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid purchase order code", response = ApiError.class),
            @ApiResponse(code = 404, message = "Purchased order not found", response = ApiError.class)
    })
    PurchaseOrderModel findByCode(@ApiParam(value = "Purchase order code", example = "9197a815-51b9-4fd1-9f74-6e0c55a683d7", required = true) String code);

    @ApiOperation("Register a new purchase order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Purchase order created with success")
    })
    PurchaseOrderModel save(@ApiParam(name = "body", value = "Representation of a new purchase order", required = true) PurchaseOrderInput purchaseOrderInput);

}
