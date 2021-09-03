package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.assembler.TeamInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.TeamModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.TeamModel;
import arn.filipe.fooddelivery.api.v1.openapi.controller.UserTeamControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.model.User;
import arn.filipe.fooddelivery.domain.service.TeamService;
import arn.filipe.fooddelivery.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users/{userId}/teams", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserTeamController implements UserTeamControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamModelAssembler teamModelAssembler;

    @Autowired
    private TeamInputDisassembler teamInputDisassembler;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    @CheckSecurity.UsersTeamsPermissions.CanFind
    @Override
    @GetMapping
    public CollectionModel<TeamModel> listAll(@PathVariable Long userId) {
        User user = userService.verifyIfExistsOrThrow(userId);

        CollectionModel<TeamModel> teamsModel =
                teamModelAssembler.toCollectionModel(user.getTeams())
                    .removeLinks();

        if(security.canEditUsersTeamsPermissions()){
            teamsModel.add(buildLinks.linkToTeamUser(userId))
                    .add(buildLinks.linkToTeamUserAssociation(userId, "associate"));

            teamsModel.getContent().forEach(teamModel -> {
                teamModel.add(buildLinks.linkToTeamUserDisassociation(
                        userId, teamModel.getId(), "disassociate"));
            });
        }

        return teamsModel;
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @PutMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long teamId){
        userService.associateTeam(userId, teamId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @DeleteMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long userId, @PathVariable Long teamId){
        userService.disassociateTeam(userId, teamId);
        return ResponseEntity.noContent().build();
    }
}
