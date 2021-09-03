package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.RestaurantController;
import arn.filipe.fooddelivery.api.v1.model.RestaurantSummaryModel;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class RestaurantSummaryModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantSummaryModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    public RestaurantSummaryModelAssembler(){
        super(RestaurantController.class, RestaurantSummaryModel.class);
    }

    @Override
    public RestaurantSummaryModel toModel(Restaurant restaurant){
        RestaurantSummaryModel restaurantSummaryModelModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantSummaryModelModel);

        if(security.canFindRestaurants()){
            restaurantSummaryModelModel.add(buildLinks.linkToRestaurant("restaurants"));
            restaurantSummaryModelModel.add(buildLinks.linkToResponsibleRestaurant(restaurantSummaryModelModel.getId(), "responsible-restaurant"));
        }

        if(security.canFindPaymentWays()){
            restaurantSummaryModelModel.add(buildLinks.linkToPaymentWayRestaurant(restaurantSummaryModelModel.getId(), "payment-way-restaurant"));
        }

        if(security.canFindKitchens()){
            restaurantSummaryModelModel.getKitchen().add(buildLinks.linkToKitchen(restaurant.getKitchen().getId(), "kitchen"));
        }

        return restaurantSummaryModelModel;
    }

    @Override
    public CollectionModel<RestaurantSummaryModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantSummaryModel> collectionModel = super.toCollectionModel(entities);

        if(security.canFindRestaurants()){
            collectionModel.add(linkTo(RestaurantController.class).withSelfRel());
        }

        return collectionModel;
    }
}
