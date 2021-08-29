package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.CityController;
import arn.filipe.fooddelivery.api.controller.StateController;
import arn.filipe.fooddelivery.api.model.CityModel;
import arn.filipe.fooddelivery.api.model.StateModel;
import arn.filipe.fooddelivery.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StateModelAssembler extends RepresentationModelAssemblerSupport<State, StateModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public StateModelAssembler(){
        super(StateController.class, StateModel.class);
    }

    @Override
    public StateModel toModel(State state){
        StateModel stateModel = createModelWithId(state.getId(), state);

        modelMapper.map(state, stateModel);

        stateModel.add(buildLinks.linkToState("states"));

        return stateModel;
    }

    @Override
    public CollectionModel<StateModel> toCollectionModel(Iterable<? extends State> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(StateController.class).withSelfRel());
    }
}
