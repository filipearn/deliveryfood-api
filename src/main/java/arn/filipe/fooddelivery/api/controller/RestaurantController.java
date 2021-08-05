package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findById(@PathVariable Long id){
        Restaurant restaurant = restaurantService.findById(id);

        if(restaurant != null){
            return ResponseEntity.ok().body(restaurant);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Restaurant restaurant){
        try{
            restaurant = restaurantService.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        try{
            restaurant = restaurantService.update(id, restaurant);
            if(restaurant != null){
                return ResponseEntity.ok().body(restaurant);
            }
            else{
                return ResponseEntity.notFound().build();
            }

        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurant> delete(@PathVariable Long id){
        try{
            restaurantService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}
