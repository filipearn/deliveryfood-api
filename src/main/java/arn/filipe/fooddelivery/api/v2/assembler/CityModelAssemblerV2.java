package arn.filipe.fooddelivery.api.v2.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.CityController;
import arn.filipe.fooddelivery.api.v1.model.CityModel;
import arn.filipe.fooddelivery.api.v2.BuildLinksV2;
import arn.filipe.fooddelivery.api.v2.controller.CityControllerV2;
import arn.filipe.fooddelivery.api.v2.model.CityModelV2;
import arn.filipe.fooddelivery.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class CityModelAssemblerV2 extends RepresentationModelAssemblerSupport<City, CityModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinksV2 buildLinks;

    public CityModelAssemblerV2(){
        super(CityControllerV2.class, CityModelV2.class);
    }

    @Override
    public CityModelV2 toModel(City city){
        CityModelV2 cityModel = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityModel);

        cityModel.add(buildLinks.linkToCity("cities"));

        return cityModel;
    }

    @Override
    public CollectionModel<CityModelV2> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CityControllerV2.class).withSelfRel());
    }

}
