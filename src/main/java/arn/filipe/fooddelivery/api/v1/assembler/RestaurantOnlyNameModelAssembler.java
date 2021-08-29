package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.RestaurantController;
import arn.filipe.fooddelivery.api.v1.model.RestaurantOnlyNameModel;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class RestaurantOnlyNameModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOnlyNameModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public RestaurantOnlyNameModelAssembler(){
        super(RestaurantController.class, RestaurantOnlyNameModel.class);
    }

    @Override
    public RestaurantOnlyNameModel toModel(Restaurant restaurant){
        RestaurantOnlyNameModel restaurantOnlyNameModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantOnlyNameModel);

        restaurantOnlyNameModel.add(buildLinks.linkToRestaurant("restaurants"));

        restaurantOnlyNameModel.add(buildLinks.linkToPaymentWayRestaurant(restaurantOnlyNameModel.getId(), "payment-way-restaurant"));

        restaurantOnlyNameModel.add(buildLinks.linkToResponsibleRestaurant(restaurantOnlyNameModel.getId(), "responsible-restaurant"));

        return restaurantOnlyNameModel;
    }


    @Override
    public CollectionModel<RestaurantOnlyNameModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(RestaurantController.class).withSelfRel());
    }
}
