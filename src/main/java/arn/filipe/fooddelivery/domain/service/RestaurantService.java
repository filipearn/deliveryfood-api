package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.RestaurantNotFoundException;
import arn.filipe.fooddelivery.domain.model.*;
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

    @Autowired
    private CityService cityService;

    @Autowired
    private PaymentWayService paymentWayService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Transactional
    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Long cityId = restaurant.getAddress().getCity().getId();

        Kitchen kitchen = kitchenService.findById(kitchenId);
        City city = cityService.findById(cityId);

        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);

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
    public void activate(List<Long> restaurantIds){
        restaurantIds.forEach(this::activate);
    }

    @Transactional
    public void deactivate(List<Long> restaurantIds){
        restaurantIds.forEach(this::deactivate);
    }

    @Transactional
    public void deactivate(Long id){
        Restaurant restaurant = verifyIfExistsOrThrow(id);

        restaurant.deactivate();
    }

    @Transactional
    public void opening(Long id){
        Restaurant restaurant = verifyIfExistsOrThrow(id);

        restaurant.open();
    }

    @Transactional
    public void closure(Long id){
        Restaurant restaurant = verifyIfExistsOrThrow(id);

        restaurant.close();
    }

    @Transactional
    public void associatePaymentWay(Long restaurantId, Long paymentWayId){
        Restaurant restaurant = verifyIfExistsOrThrow(restaurantId);
        PaymentWay paymentWay = paymentWayService.verifyIfExistsOrThrow(paymentWayId);

        restaurant.associatePaymentWay(paymentWay);
    }

    @Transactional
    public void disassociatePaymentWay(Long restaurantId, Long paymentWayId){
        Restaurant restaurant = verifyIfExistsOrThrow(restaurantId);
        PaymentWay paymentWay = paymentWayService.verifyIfExistsOrThrow(paymentWayId);

        restaurant.disassociatePaymentWay(paymentWay);
    }

    @Transactional
    public void associateProduct(Long restaurantId, Long productId){
        Restaurant restaurant = verifyIfExistsOrThrow(restaurantId);
        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        restaurant.associateProduct(product);
    }

    @Transactional
    public void disassociateProduct(Long restaurantId, Long productId){
        Restaurant restaurant = verifyIfExistsOrThrow(restaurantId);
        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        restaurant.disassociateProduct(product);
    }

    @Transactional
    public void associateUser(Long restaurantId, Long userId){
        Restaurant restaurant = verifyIfExistsOrThrow(restaurantId);
        User user = userService.verifyIfExistsOrThrow(userId);

        restaurant.associateUser(user);
    }

    @Transactional
    public void disassociateUser(Long restaurantId, Long userId){
        Restaurant restaurant = verifyIfExistsOrThrow(restaurantId);
        User user = userService.verifyIfExistsOrThrow(userId);

        restaurant.disassociateUser(user);
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
