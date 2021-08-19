package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.input.PermissionInput;
import arn.filipe.fooddelivery.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permission toDomainObject(PermissionInput permissionInput){
        return modelMapper.map(permissionInput, Permission.class);
    }

    public void copyToDomainObject(PermissionInput permissionInput, Permission permission){
        modelMapper.map(permissionInput, permission);
    }
}
