package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.RestaurantController;
import arn.filipe.fooddelivery.api.model.RestaurantModel;
import arn.filipe.fooddelivery.api.model.RestaurantSummaryModel;
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

    public RestaurantSummaryModelAssembler(){
        super(RestaurantController.class, RestaurantSummaryModel.class);
    }

    @Override
    public RestaurantSummaryModel toModel(Restaurant restaurant){
        RestaurantSummaryModel restaurantSummaryModelModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantSummaryModelModel);

        restaurantSummaryModelModel.add(buildLinks.linkToRestaurant("restaurants"));

        restaurantSummaryModelModel.add(buildLinks.linkToPaymentWayRestaurant(restaurantSummaryModelModel.getId(), "payment-way-restaurant"));

        restaurantSummaryModelModel.add(buildLinks.linkToResponsibleRestaurant(restaurantSummaryModelModel.getId(), "responsible-restaurant"));

        restaurantSummaryModelModel.getKitchen().add(buildLinks.linkToKitchen(restaurant.getKitchen().getId(), "kitchen"));

        return restaurantSummaryModelModel;
    }

    @Override
    public CollectionModel<RestaurantSummaryModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(RestaurantController.class).withSelfRel());
    }
}
