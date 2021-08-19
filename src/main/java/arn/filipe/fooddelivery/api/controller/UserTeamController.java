package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.TeamInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.TeamModelAssembler;
import arn.filipe.fooddelivery.api.model.TeamModel;
import arn.filipe.fooddelivery.api.model.input.TeamInput;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.model.User;
import arn.filipe.fooddelivery.domain.service.TeamService;
import arn.filipe.fooddelivery.domain.service.TeamService;
import arn.filipe.fooddelivery.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/teams")
public class UserTeamController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamModelAssembler teamModelAssembler;

    @Autowired
    private TeamInputDisassembler teamInputDisassembler;

    @GetMapping
    public List<TeamModel> listAll(@PathVariable Long userId){
        User user = userService.verifyIfExistsOrThrow(userId);

        return teamModelAssembler.toCollectionModel(user.getTeams());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamModel save(@PathVariable Long userId, @RequestBody @Valid TeamInput teamInput){
        User user = userService.verifyIfExistsOrThrow(userId);

        Team team = teamInputDisassembler.toDomainObject(teamInput);

        user.associateTeam(team);

        return teamModelAssembler.toModel(teamService.save(team));
    }

    @PutMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long userId, @PathVariable Long teamId){
        userService.associateTeam(userId, teamId);
    }

    @DeleteMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long userId, @PathVariable Long teamId){
        userService.disassociateTeam(userId, teamId);
    }
}
