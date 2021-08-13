package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.exception.KitchenNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> listAll(){
        return restaurantService.listAll();
    }

    @GetMapping("/{id}")
    public Restaurant findById(@PathVariable Long id){
            return restaurantService.findById(id);
    }

    @GetMapping("/name-and-kitchen")
    public List<Restaurant> findByNameAndKitchen(String name, Long kitchenId){
        return restaurantService.findByNameAndKitchen(name, kitchenId);
    }

    @GetMapping("/name-and-freighRate")
    public List<Restaurant> findByNameAndfreighRate(String name, BigDecimal freighRateInicial, BigDecimal freighRateFinal){
        return restaurantService.findByNameAndfreighRate(name, freighRateInicial, freighRateFinal);
    }

    @GetMapping("/free-shipping")
    public List<Restaurant> withFreeShipping(String name){
        return restaurantService.withFreeShipping(name);
    }

    @GetMapping("/first")
    public ResponseEntity<?> findFirst(){
        try{
            return ResponseEntity.ok().body(restaurantService.findFirst());
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant save(@RequestBody Restaurant restaurant){
        try{
            return restaurantService.save(restaurant);
        } catch (KitchenNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        try{
            return restaurantService.update(id, restaurant);
        } catch (KitchenNotFoundException e){
        throw new BusinessException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}")
    public Restaurant partialUpdate(@PathVariable Long id,
                                        @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        Restaurant actualRestaurant = restaurantService.findById(id);

        merge(fields, actualRestaurant, request);

        return update(id, actualRestaurant);
    }

    private void merge(Map<String, Object> originData, Restaurant destinationRestaurant,
                       HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant originRestaurant = objectMapper.convertValue(originData, Restaurant.class);

            originData.forEach((propertyName, propertyValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, originRestaurant);

                ReflectionUtils.setField(field, destinationRestaurant, newValue);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        restaurantService.delete(id);
    }

}
