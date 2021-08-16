package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.api.model.RestaurantModel;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.exception.RestaurantNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import arn.filipe.fooddelivery.infrastructure.repository.spec.RestaurantSpecFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    public static final String RESTAURANT_NOT_FOUND = "Restaurant with id %d not found.";
    public static final String RESTAURANT_IN_USER = "Restaurant with id %d can't be removed. Resource in use.";
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
    public Restaurant update(Long id, Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenService.findById(kitchenId);

        Restaurant restaurantToUpdate = verifyIfExistsOrThrow(id);

        BeanUtils.copyProperties(restaurant, restaurantToUpdate, "id", "paymentWay", "address", "registrationDate", "products");

        restaurantToUpdate.setKitchen(kitchen);

        return restaurantRepository.save(restaurantToUpdate);
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
                    String.format(RESTAURANT_IN_USER, id));
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
