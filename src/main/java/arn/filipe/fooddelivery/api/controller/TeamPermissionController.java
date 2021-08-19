package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.PermissionInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.PermissionModelAssembler;
import arn.filipe.fooddelivery.api.model.PermissionModel;
import arn.filipe.fooddelivery.api.model.input.PermissionInput;
import arn.filipe.fooddelivery.domain.model.Permission;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.service.PermissionService;
import arn.filipe.fooddelivery.domain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teams/{teamId}/permissions")
public class TeamPermissionController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private PermissionInputDisassembler permissionInputDisassembler;

    @GetMapping
    public List<PermissionModel> listAll(@PathVariable Long teamId){
        Team team = teamService.verifyIfExistsOrThrow(teamId);

        return permissionModelAssembler.toCollectionModel(team.getPermissions());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionModel save(@PathVariable Long teamId, @RequestBody @Valid PermissionInput permissionInput){
        Team team = teamService.verifyIfExistsOrThrow(teamId);

        Permission permission = permissionInputDisassembler.toDomainObject(permissionInput);

        team.associatePermission(permission);

        return permissionModelAssembler.toModel(permissionService.save(permission));
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long teamId, @PathVariable Long permissionId){

        teamService.associatePermission(teamId, permissionId);
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long teamId, @PathVariable Long permissionId){

        teamService.disassociatePermission(teamId, permissionId);
    }
}
