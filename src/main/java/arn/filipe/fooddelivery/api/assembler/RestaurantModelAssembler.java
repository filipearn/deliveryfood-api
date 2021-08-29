package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.RestaurantController;
import arn.filipe.fooddelivery.api.model.RestaurantModel;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class RestaurantModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public RestaurantModelAssembler(){
        super(RestaurantController.class, RestaurantModel.class);
    }

    @Override
    public RestaurantModel toModel(Restaurant restaurant){
        RestaurantModel restaurantModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantModel);

        restaurantModel.add(buildLinks.linkToRestaurant("restaurants"));

        restaurantModel.add(buildLinks.linkToPaymentWayRestaurant(restaurantModel.getId(), "payment-way-restaurant"));

        restaurantModel.add(buildLinks.linkToResponsibleRestaurant(restaurantModel.getId(), "responsible-restaurant"));

        restaurantModel.add(buildLinks.linkToRestaurantProducts(restaurantModel.getId(), "products-restaurant"));

        restaurantModel.getKitchen().add(buildLinks.linkToKitchen(restaurant.getKitchen().getId()));

        if(restaurantModel.getAddress() != null && restaurantModel.getAddress().getCity() != null){
            restaurantModel.getAddress().getCity().add(buildLinks.linkToCity(restaurant.getAddress().getCity().getId()));
        }

        if(restaurant.allowedOpen()){
            restaurantModel.add(buildLinks.linkToRestaurantOpening(restaurant.getId(), "open"));
        }

        if(restaurant.allowedClosure()){
            restaurantModel.add(buildLinks.linkToRestaurantClosure(restaurant.getId(), "close"));
        }

        if(restaurant.allowedActivation()){
            restaurantModel.add(buildLinks.linkToRestaurantActivation(restaurant.getId(), "activate"));
        }

        if(restaurant.allowedDeactivation()){
            restaurantModel.add(buildLinks.linkToRestaurantDeactivation(restaurant.getId(), "deactivate"));
        }

        return restaurantModel;
    }


    @Override
    public CollectionModel<RestaurantModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(RestaurantController.class).withSelfRel());
    }
}
