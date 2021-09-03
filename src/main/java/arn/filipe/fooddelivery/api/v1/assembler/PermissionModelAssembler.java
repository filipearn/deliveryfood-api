package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.PermissionController;
import arn.filipe.fooddelivery.api.v1.controller.TeamPermissionController;
import arn.filipe.fooddelivery.api.v1.model.PermissionModel;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PermissionModelAssembler extends RepresentationModelAssemblerSupport<Permission, PermissionModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    public PermissionModelAssembler(){
        super(TeamPermissionController.class, PermissionModel.class);
    }

    public PermissionModel toModel(Permission permission){
        PermissionModel permissionModel = modelMapper.map(permission, PermissionModel.class);

        return permissionModel;
    }

    @Override
    public CollectionModel<PermissionModel> toCollectionModel(Iterable<? extends Permission> entities) {
        CollectionModel<PermissionModel> collectionModel =  super.toCollectionModel(entities);

        if(security.canFindUsersTeamsPermissions()){
            collectionModel.add(linkTo(PermissionController.class).withSelfRel());
        }

        return collectionModel;
    }
}
