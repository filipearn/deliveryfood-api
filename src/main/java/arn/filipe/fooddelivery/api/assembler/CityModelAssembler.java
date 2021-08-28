package arn.filipe.fooddelivery.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import arn.filipe.fooddelivery.api.controller.CityController;
import arn.filipe.fooddelivery.api.controller.StateController;
import arn.filipe.fooddelivery.api.model.CityModel;
import arn.filipe.fooddelivery.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityModel> {

    @Autowired
    private ModelMapper modelMapper;

    public CityModelAssembler(){
        super(CityController.class, CityModel.class);
    }

    @Override
    public CityModel toModel(City city){
        CityModel cityModel = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityModel);

        cityModel.add(linkTo(methodOn(CityController.class)
                .listAll()).withRel("cities"));

        cityModel.getState().add(linkTo(methodOn(StateController.class)
                .findById(cityModel.getState().getId())).withSelfRel());

        return cityModel;
    }

    @Override
    public CollectionModel<CityModel> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CityController.class).withSelfRel());
    }

}
