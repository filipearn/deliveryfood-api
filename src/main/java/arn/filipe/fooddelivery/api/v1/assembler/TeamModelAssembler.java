package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.TeamController;
import arn.filipe.fooddelivery.api.v1.model.TeamModel;
import arn.filipe.fooddelivery.domain.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
