package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.TeamModel;
import arn.filipe.fooddelivery.domain.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TeamModel toModel(Team Team){
        return modelMapper.map(Team, TeamModel.class);
    }

    public List<TeamModel> toCollectionModel(List<Team> teams){
        return teams.stream()
                .map(team -> toModel(team))
                .collect(Collectors.toList());
    }
}
