package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.TeamPermissionController;
import arn.filipe.fooddelivery.api.model.PermissionModel;
import arn.filipe.fooddelivery.api.model.PhotoProductModel;
import arn.filipe.fooddelivery.domain.model.Permission;
import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionModelAssembler extends RepresentationModelAssemblerSupport<Permission, PermissionModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public PermissionModelAssembler(){
        super(TeamPermissionController.class, PermissionModel.class);
    }

    public PermissionModel toModel(Permission permission){
        PermissionModel permissionModel = modelMapper.map(permission, PermissionModel.class);

        return permissionModel;
    }

    @Override
    public CollectionModel<PermissionModel> toCollectionModel(Iterable<? extends Permission> entities) {
        return super.toCollectionModel(entities);
    }
}
