package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.PermissionModel;
import arn.filipe.fooddelivery.api.model.input.PermissionInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Teams")
public interface TeamPermissionControllerOpenApi {

    @ApiOperation("List the teams permissions")
    List<PermissionModel> listAll(@ApiParam(value = "Team id", example = "1", required = true) Long teamId);

    @ApiOperation("Register a new team permission")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Permission created with success"),
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    PermissionModel save(@ApiParam(value = "Team id", example = "1", required = true) Long teamId,
                         @ApiParam(name = "body", value = "Representation of a new team permission", required = true) PermissionInput permissionInput);

    @ApiOperation("Associate a permission with a team")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    void associate(@ApiParam(value = "Team id", example = "1", required = true) Long teamId,
                   @ApiParam(value = "Team id", example = "1", required = true) Long permissionId);

    @ApiOperation("Disassociate a permission with a team")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    void disassociate(@ApiParam(value = "Team id", example = "1", required = true) Long teamId,
                      @ApiParam(value = "Team id", example = "1", required = true) Long permissionId);
}
