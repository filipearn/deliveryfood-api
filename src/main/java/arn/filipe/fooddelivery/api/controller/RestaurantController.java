package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.RestaurantInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.RestaurantModelAssembler;
import arn.filipe.fooddelivery.api.model.RestaurantModel;
import arn.filipe.fooddelivery.api.model.input.RestaurantInput;
import arn.filipe.fooddelivery.core.validation.ValidationException;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.KitchenNotFoundException;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestaurantInputDisassembler restaurantInputDisassembler;

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @GetMapping
    public List<RestaurantModel> listAll(){
        return restaurantModelAssembler.toCollectionModel(restaurantService.listAll());
    }


    @GetMapping("/{id}")
    public Restaurant findById(@PathVariable Long id){
            return restaurantService.findById(id);
    }

    @GetMapping("/name-and-kitchen")
    public List<Restaurant> findByNameAndKitchen(String name, Long kitchenId){
        return restaurantService.findByNameAndKitchen(name, kitchenId);
    }

    @GetMapping("/name-and-freightRate")
    public List<Restaurant> findByNameAndFreightRate(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal){
        return restaurantService.findByNameAndFreightRate(name, freightRateInitial, freightRateFinal);
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
    public RestaurantModel save(@RequestBody @Valid RestaurantInput restaurantInput){
        try{
            Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);

            return restaurantModelAssembler.toModel(restaurantService.save(restaurant));
        } catch (KitchenNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestaurantModel update(@PathVariable Long id, @RequestBody @Valid RestaurantInput restaurantInput){
        try{
            Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(id);

            restaurantInputDisassembler.copyToDomainObject(restaurantInput, restaurant);

            return restaurantModelAssembler.toModel(restaurantService.save(restaurant));
        } catch (KitchenNotFoundException e){
        throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long id){
        restaurantService.activate(id);
    }

    @DeleteMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id){
        restaurantService.deactivate(id);
    }

//    @PatchMapping("/{id}")
//    public RestaurantModel partialUpdate(@PathVariable Long id,
//                                        @RequestBody Map<String, Object> fields, HttpServletRequest request) {
//        Restaurant actualRestaurant = restaurantService.findById(id);
//
//        merge(fields, actualRestaurant, request);
//
//        validate(actualRestaurant, "restaurant");
//
//        return restaurantModelAssembler.toModel(update(id, actualRestaurant));
//    }

    private void validate(Restaurant restaurant, String objectName) {

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant,objectName);
        validator.validate(restaurant, bindingResult);

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult);
        }
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
