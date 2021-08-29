package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.model.PermissionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "permissions")
public interface PermissionControllerOpenApi {

    @ApiOperation("List the permissions")
    CollectionModel<PermissionModel> listAll();
}
