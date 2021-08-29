package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.v1.model.PermissionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissions")
public interface PermissionControllerOpenApi {

    @ApiOperation("List the permissions")
    CollectionModel<PermissionModel> listAll();
}
