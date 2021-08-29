package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.TeamController;
import arn.filipe.fooddelivery.api.controller.UserTeamController;
import arn.filipe.fooddelivery.api.model.PhotoProductModel;
import arn.filipe.fooddelivery.api.model.TeamModel;
import arn.filipe.fooddelivery.domain.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamModelAssembler extends RepresentationModelAssemblerSupport<Team, TeamModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public TeamModelAssembler(){
        super(TeamController.class, TeamModel.class);
    }

    public TeamModel toModel(Team team){
        TeamModel teamModel = modelMapper.map(team, TeamModel.class);

        teamModel.add(buildLinks.linkToTeam(team.getId()));

        teamModel.add(buildLinks.linkToTeam("teams"));

        teamModel.add(buildLinks.linkToTeamPermission(team.getId(), "permissions"));

        return teamModel;
    }

    @Override
    public CollectionModel<TeamModel> toCollectionModel(Iterable<? extends Team> entities) {
        return super.toCollectionModel(entities)
                .add(buildLinks.linkToTeam());
    }
}
