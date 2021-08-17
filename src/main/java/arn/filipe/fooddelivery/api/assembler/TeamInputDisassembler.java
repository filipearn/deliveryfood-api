package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.input.TeamInput;
import arn.filipe.fooddelivery.domain.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Team toDomainObject(TeamInput teamInput){
        return modelMapper.map(teamInput, Team.class);
    }

    public void copyToDomainObject(TeamInput teamInput, Team team){
        modelMapper.map(teamInput, team);
    }
}
