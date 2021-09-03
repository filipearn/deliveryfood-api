package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.assembler.PermissionInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.PermissionModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.PermissionModel;
import arn.filipe.fooddelivery.api.v1.model.input.PermissionInput;
import arn.filipe.fooddelivery.api.v1.openapi.controller.TeamPermissionControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.domain.model.Permission;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.service.PermissionService;
import arn.filipe.fooddelivery.domain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/teams/{teamId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamPermissionController implements TeamPermissionControllerOpenApi {

    @Autowired
    private TeamService teamService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private PermissionInputDisassembler permissionInputDisassembler;

    @Autowired
    private BuildLinks buildLinks;

    @CheckSecurity.UsersTeamsPermissions.CanFind
    @Override
    @GetMapping
    public CollectionModel<PermissionModel> listAll(@PathVariable Long teamId){
        Team team = teamService.verifyIfExistsOrThrow(teamId);

        CollectionModel<PermissionModel> permissionsModel
                = permissionModelAssembler.toCollectionModel(team.getPermissions())
                .removeLinks()
                .add(buildLinks.linkToTeamPermission(teamId))
                .add(buildLinks.linkToTeamPermissionAssociation(teamId, "associate"));

        permissionsModel.getContent().forEach(permissionModel -> {
            permissionModel.add(buildLinks.linkToTeamPermissionDisassociation(
                    teamId, permissionModel.getId(), "disassociate"));
        });

        return permissionsModel;
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionModel save(@PathVariable Long teamId, @RequestBody @Valid PermissionInput permissionInput){
        Team team = teamService.verifyIfExistsOrThrow(teamId);

        Permission permission = permissionInputDisassembler.toDomainObject(permissionInput);

        team.associatePermission(permission);

        return permissionModelAssembler.toModel(permissionService.save(permission));
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long teamId, @PathVariable Long permissionId){

        teamService.associatePermission(teamId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long teamId, @PathVariable Long permissionId){

        teamService.disassociatePermission(teamId, permissionId);
        return ResponseEntity.noContent().build();
    }
}
