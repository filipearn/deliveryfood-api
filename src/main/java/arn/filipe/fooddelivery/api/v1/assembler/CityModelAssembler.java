package arn.filipe.fooddelivery.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.CityController;
import arn.filipe.fooddelivery.api.v1.model.CityModel;
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

    @Autowired
    private BuildLinks buildLinks;

    public CityModelAssembler(){
        super(CityController.class, CityModel.class);
    }

    @Override
    public CityModel toModel(City city){
        CityModel cityModel = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityModel);

        cityModel.add(buildLinks.linkToCity("cities"));

        cityModel.getState().add(buildLinks.linkToState(cityModel.getState().getId(), "state"));

        return cityModel;
    }

    @Override
    public CollectionModel<CityModel> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CityController.class).withSelfRel());
    }

}
