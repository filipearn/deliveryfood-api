package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.TeamModel;
import arn.filipe.fooddelivery.api.model.input.TeamInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Teams")
public interface TeamControllerOpenApi {

    @ApiOperation("List the teams")
    CollectionModel<TeamModel> listAll();

    @ApiOperation("List a team by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid team id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    TeamModel findById(@ApiParam(value = "Team id", example = "1", required = true) Long id);


    @ApiOperation("Register a new team")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Team created with success")
    })
    TeamModel save(@ApiParam(name = "body", value = "Representation of a new team", required = true) TeamInput TeamInput);


    @ApiOperation("Update a team by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Team updated with success"),
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    TeamModel update(@ApiParam(value = "Team id", example = "1", required = true) Long id,
                     @ApiParam(name = "body", value = "Representation of a team with new data", required = true) TeamInput teamInput);


    @ApiOperation("Delete a team by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product updated with success"),
            @ApiResponse(code = 400, message = "Invalid team id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    void delete(@ApiParam(value = "Team id", example = "1", required = true) Long id);
}
