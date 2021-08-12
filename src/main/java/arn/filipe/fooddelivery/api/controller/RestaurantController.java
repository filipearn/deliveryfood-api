package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
        } catch (EntityNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        try{
            return restaurantService.update(id, restaurant);
        } catch (EntityNotFoundException e){
        throw new BusinessException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        restaurantService.delete(id);
    }

}
