package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.KitchenModel;
import arn.filipe.fooddelivery.api.v1.model.input.KitchenInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Kitchens")
public interface KitchenControllerOpenApi {

    @ApiOperation("List the kitchens with pagination")
    PagedModel<KitchenModel> listAll(Pageable pageable);

    @ApiOperation("List the kitchens with name containing")
    CollectionModel<KitchenModel> findByNameContaining(@ApiParam(value = "Kitchen name", example = "Brasileira") String name);

    @ApiOperation("Find a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid kitchen id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Kitchen not found", response = ApiError.class)
    })
    KitchenModel findById(@ApiParam(value = "Kitchen id", example = "1", required = true) Long id);

    @ApiOperation("Register a new kitchen")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Kitchen created with success")
    })
    KitchenModel save(@ApiParam(name = "body", value = "Representation of a kitchen with new data", required = true)KitchenInput kitchenInput);

    @ApiOperation("Update a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Kitchen updated with success"),
            @ApiResponse(code = 404, message = "Kitchen not found", response = ApiError.class)
    })
    KitchenModel update(@ApiParam(value = "Kitchen id", example = "1", required = true) Long id,
                        @ApiParam(name = "body", value = "Representation of a kitchen with new data", required = true) KitchenInput kitchenInput);

    @ApiOperation("Delete a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid kitchen id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Kitchen not found", response = ApiError.class)
    })
    void delete(@ApiParam(value = "Kitchen id", example = "1", required = true)Long id);
}
