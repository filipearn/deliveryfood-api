package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.StateModel;
import arn.filipe.fooddelivery.api.v1.model.input.StateInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "States")
public interface StateControllerOpenApi {

    @ApiOperation("List the states")
    CollectionModel<StateModel> listAll();

    @ApiOperation("Find a state by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid state id", response = ApiError.class),
            @ApiResponse(code = 404, message = "State not found", response = ApiError.class)
    })
    StateModel findById(@ApiParam(value = "State id", example = "1", required = true) Long id);

    @ApiOperation("Register a new state")
    @ApiResponses({
            @ApiResponse(code = 201, message = "State created with success")
    })
    StateModel save(@ApiParam(name = "body", value = "Representation of a new state", required = true)
                          StateInput stateInput);


    @ApiOperation("Update a state by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "State updated with success"),
            @ApiResponse(code = 404, message = "State not found", response = ApiError.class)
    })
    StateModel update(@ApiParam(value = "State id", example = "1", required = true) Long id,
                            @ApiParam(name = "body", value = "Representation of a state with new data", required = true)
                            StateInput stateInput);

    @ApiOperation("Delete a state by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid state id", response = ApiError.class),
            @ApiResponse(code = 404, message = "State not found", response = ApiError.class)
    })
    void delete(@ApiParam(value = "State id", example = "1", required = true) Long id);
}
