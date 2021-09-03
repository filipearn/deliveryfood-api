package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.assembler.TeamInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.TeamModelAssembler;
import arn.filipe.fooddelivery.api.v1.openapi.controller.TeamControllerOpenApi;
import arn.filipe.fooddelivery.api.v1.model.TeamModel;
import arn.filipe.fooddelivery.api.v1.model.input.TeamInput;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/teams", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController implements TeamControllerOpenApi {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamInputDisassembler TeamInputDisassembler;

    @Autowired
    private TeamModelAssembler TeamModelAssembler;

    @CheckSecurity.UsersTeamsPermissions.CanFind
    @Override
    @GetMapping
    public CollectionModel<TeamModel> listAll(){
        return TeamModelAssembler.toCollectionModel(teamService.listAll());
    }

    @CheckSecurity.UsersTeamsPermissions.CanFind
    @Override
    @GetMapping("/{id}")
    public TeamModel findById(@PathVariable Long id){
        return TeamModelAssembler.toModel(teamService.findById(id));
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamModel save(@RequestBody @Valid TeamInput TeamInput){
        Team team = TeamInputDisassembler.toDomainObject(TeamInput);

        return TeamModelAssembler.toModel(teamService.save(team));
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @PutMapping("/{id}")
    public TeamModel update(@PathVariable Long id, @RequestBody @Valid TeamInput teamInput){
        Team team = teamService.verifyIfExistsOrThrow(id);

        TeamInputDisassembler.copyToDomainObject(teamInput, team);

        return TeamModelAssembler.toModel(teamService.save(team));
    }

    @CheckSecurity.UsersTeamsPermissions.CanEdit
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        teamService.delete(id);
    }
}
