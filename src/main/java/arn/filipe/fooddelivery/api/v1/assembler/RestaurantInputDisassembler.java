package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.model.input.RestaurantInput;
import arn.filipe.fooddelivery.domain.model.City;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurant toDomainObject(RestaurantInput restaurantInput){
        return modelMapper.map(restaurantInput, Restaurant.class);
    }

    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant){
        // To avoid org.hibernate.HibernateException: identifier of an instance of
        // domain.model.kitchen was altered from 1 to 2
        restaurant.setKitchen(new Kitchen());

        if(restaurant.getAddress() != null){
            restaurant.getAddress().setCity(new City());
        }

        modelMapper.map(restaurantInput, restaurant);
    }
}
