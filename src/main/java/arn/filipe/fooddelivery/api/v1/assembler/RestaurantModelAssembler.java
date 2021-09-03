package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.RestaurantController;
import arn.filipe.fooddelivery.api.v1.model.RestaurantModel;
import arn.filipe.fooddelivery.core.security.Security;
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

    @Autowired
    private Security security;

    public RestaurantModelAssembler(){
        super(RestaurantController.class, RestaurantModel.class);
    }

    @Override
    public RestaurantModel toModel(Restaurant restaurant){
        RestaurantModel restaurantModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantModel);

        if(security.canFindRestaurants()){
            restaurantModel.add(buildLinks.linkToRestaurant("restaurants"));
        }

        if(security.canManageRestaurantRegistration()){
            if(restaurant.allowedActivation()){
                restaurantModel.add(buildLinks.linkToRestaurantActivation(restaurant.getId(), "activate"));
            }

            if(restaurant.allowedDeactivation()){
                restaurantModel.add(buildLinks.linkToRestaurantDeactivation(restaurant.getId(), "deactivate"));
            }
        }

        if(security.canManageRestaurantsOperation(restaurant.getId())){
            if(restaurant.allowedOpen()){
                restaurantModel.add(buildLinks.linkToRestaurantOpening(restaurant.getId(), "open"));
            }

            if(restaurant.allowedClosure()){
                restaurantModel.add(buildLinks.linkToRestaurantClosure(restaurant.getId(), "close"));
            }
        }

        if(security.canFindPaymentWays()) {
            restaurantModel.add(buildLinks.linkToPaymentWayRestaurant(restaurantModel.getId(), "payment-way-restaurant"));
        }

        if(security.canManageRestaurantRegistration()){
            restaurantModel.add(buildLinks.linkToResponsibleRestaurant(restaurantModel.getId(), "responsible-restaurant"));
        }

        if(security.canFindRestaurants()) {
            restaurantModel.add(buildLinks.linkToRestaurantProducts(restaurantModel.getId(), "products-restaurant"));
        }

        if(security.canFindKitchens()) {
            restaurantModel.getKitchen().add(buildLinks.linkToKitchen(restaurant.getKitchen().getId()));
        }

        if(security.canFindCities()){
            if(restaurantModel.getAddress() != null && restaurantModel.getAddress().getCity() != null){
                restaurantModel.getAddress().getCity().add(buildLinks.linkToCity(restaurant.getAddress().getCity().getId()));
            }
        }

        return restaurantModel;
    }


    @Override
    public CollectionModel<RestaurantModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantModel> collectionModel = super.toCollectionModel(entities);

        if(security.canFindRestaurants()){
            collectionModel.add(linkTo(RestaurantController.class).withSelfRel());
        }

        return collectionModel;

    }
}
