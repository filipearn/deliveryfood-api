package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.UserModel;
import arn.filipe.fooddelivery.api.v1.model.input.PasswordInput;
import arn.filipe.fooddelivery.api.v1.model.input.UserInput;
import arn.filipe.fooddelivery.api.v1.model.input.UserWithPasswordInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Users")
public interface UserControllerOpenApi {

    @ApiOperation("List the users")
    CollectionModel<UserModel> listAll();

    @ApiOperation("List a user by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid user id", response = ApiError.class),
            @ApiResponse(code = 404, message = "User not found", response = ApiError.class)
    })
    UserModel findById(@ApiParam(value = "User id", example = "1", required = true) Long id);

    @ApiOperation("Register a new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User registered with success")
    })
    UserModel save(@ApiParam(value = "body", example = "Represents the user with new data", required = true)
                           UserWithPasswordInput userInput);

    @ApiOperation("Update a user by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User updated with success"),
            @ApiResponse(code = 400, message = "Invalid user id", response = ApiError.class),
            @ApiResponse(code = 404, message = "User not found", response = ApiError.class)
    })
    UserModel update(@ApiParam(value = "User id", example = "1", required = true) Long id,
                      @ApiParam(value = "body", example = "Represents the user with new data", required = true)
                              UserInput userInput);

    @ApiOperation("Change a user password by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Password changed with success"),
            @ApiResponse(code = 400, message = "Invalid user id", response = ApiError.class),
            @ApiResponse(code = 404, message = "User not found", response = ApiError.class)
    })
    void changePassword(@ApiParam(value = "User id", example = "1", required = true) Long id,
                        @ApiParam(value = "body", example = "Represents the new user password", required = true)
                                PasswordInput passwordInput);
}
