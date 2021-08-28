package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.CityModel;
import arn.filipe.fooddelivery.api.model.input.CityInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Cities")
public interface CityControllerOpenApi {

    @ApiOperation("List the cities")
    CollectionModel<CityModel> listAll();

    @ApiOperation("Find a city by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid city id", response = ApiError.class),
            @ApiResponse(code = 404, message = "City not found", response = ApiError.class)
    })
    CityModel findById(@ApiParam(value = "City id", example = "1", required = true) Long id);

    @ApiOperation("Register a new city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created with success")
    })
    CityModel save(@ApiParam(name = "body", value = "Representation of a new city", required = true)
                          CityInput cityInput);


    @ApiOperation("Update a city by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated with success"),
            @ApiResponse(code = 404, message = "City not found", response = ApiError.class)
    })
    CityModel update(@ApiParam(value = "City id", example = "1", required = true) Long id,
                            @ApiParam(name = "body", value = "Representation of a city with new data", required = true)
                            CityInput cityInput);

    @ApiOperation("Delete a city by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid city id", response = ApiError.class),
            @ApiResponse(code = 404, message = "City not found", response = ApiError.class)
    })
    void delete(@ApiParam(value = "City id", example = "1", required = true) Long id);
}
