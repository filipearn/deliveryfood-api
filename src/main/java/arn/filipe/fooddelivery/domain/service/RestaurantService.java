package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Optional<Kitchen> kitchen = kitchenRepository.findById(kitchenId);

        if(kitchen.isEmpty()){
            throw new EntityNotFoundException(
                    String.format("Kitchen with id %d not found.", kitchenId));
        }
        else {
            restaurant.setKitchen(kitchen.get());
            return restaurantRepository.save(restaurant);
        }
    }

    public List<Restaurant> listAll(){
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id){
        return restaurantRepository.getById(id);
    }

    public Restaurant update(Long id, Restaurant restaurant){
        Restaurant restaurantToUpdate;
        Long kitchenId = restaurant.getKitchen().getId();
        Optional<Kitchen> kitchen = kitchenRepository.findById(kitchenId);

        if(kitchen.isEmpty()){
            throw new EntityNotFoundException(
                    String.format("Kitchen with id %d not found.", kitchenId));
        }

        if(restaurantRepository.findById(id).isPresent()){
            restaurantToUpdate = restaurantRepository.findById(id).get();
            BeanUtils.copyProperties(restaurant, restaurantToUpdate, "id");
            return restaurantRepository.save(restaurantToUpdate);
        }
        return null;
    }


    public void delete(Long id) {
        if (restaurantRepository.findById(id).isPresent()) {
            try {
                restaurantRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new EntityInUseException(
                        String.format("restaurant with id %d can't be removed. Resource in use.", id));
            }
        }
    }
}
