package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.RestaurantNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestaurantService {

    public static final String RESTAURANT_NOT_FOUND = "Restaurant with id %d not found.";
    public static final String RESTAURANT_IN_USE = "Restaurant with id %d can't be removed. Resource in use.";
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenService kitchenService;

    @Transactional
    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();

        Kitchen kitchen = kitchenService.findById(kitchenId);

        restaurant.setKitchen(kitchen);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> listAll(){
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id){
        return verifyIfExistsOrThrow(id);
    }

    public List<Restaurant> findByNameAndKitchen(String name, Long kitchenId){
        return restaurantRepository.queryByNameAndKitchen(name, kitchenId);
    }

    public List<Restaurant> findByNameAndFreightRate(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal){
        return restaurantRepository.customizedFind(name, freightRateInitial, freightRateFinal);
    }

    public List<Restaurant> withFreeShipping(String name){
        return restaurantRepository.withFreeShipping(name);
    }

    @Transactional
    public void activate(Long id){
        Restaurant restaurant = verifyIfExistsOrThrow(id);

        restaurant.activate();
    }

    @Transactional
    public void deactivate(Long id){
        Restaurant restaurant = verifyIfExistsOrThrow(id);

        restaurant.deactivate();
    }

    @Transactional
    public void delete(Long id) {
        try {
            restaurantRepository.deleteById(id);
            restaurantRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(RESTAURANT_IN_USE, id));
        }
    }

    public Restaurant findFirst() {
        return restaurantRepository.findFirst().orElseThrow(
                () -> new EmptyResultDataAccessException(
                        String.format(RESTAURANT_NOT_FOUND), 1));
    }

    public Restaurant verifyIfExistsOrThrow(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
