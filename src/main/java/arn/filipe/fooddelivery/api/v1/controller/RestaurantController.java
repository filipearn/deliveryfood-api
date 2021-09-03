package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.assembler.RestaurantInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.RestaurantModelAssembler;
import arn.filipe.fooddelivery.api.v1.assembler.RestaurantOnlyNameModelAssembler;
import arn.filipe.fooddelivery.api.v1.assembler.RestaurantSummaryModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.RestaurantModel;
import arn.filipe.fooddelivery.api.v1.model.RestaurantOnlyNameModel;
import arn.filipe.fooddelivery.api.v1.model.RestaurantSummaryModel;
import arn.filipe.fooddelivery.api.v1.model.input.RestaurantInput;
import arn.filipe.fooddelivery.api.v1.openapi.controller.RestaurantControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.core.validation.ValidationException;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.CityNotFoundException;
import arn.filipe.fooddelivery.domain.exception.KitchenNotFoundException;
import arn.filipe.fooddelivery.domain.exception.RestaurantNotFoundException;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/api/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestaurantInputDisassembler restaurantInputDisassembler;

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @Autowired
    private RestaurantSummaryModelAssembler restaurantSummaryModelAssembler;

    @Autowired
    private RestaurantOnlyNameModelAssembler restaurantOnlyNameModelAssembler;

    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping
    public CollectionModel<RestaurantModel> listAll(){
        return restaurantModelAssembler.toCollectionModel(restaurantService.listAll());
    }

    @CheckSecurity.Restaurants.CanFind
    //@JsonView(RestaurantView.Summary.class)
    @Override
    @GetMapping(params = "projection=summary")
    public CollectionModel<RestaurantSummaryModel> listAllSummary(){
        return restaurantSummaryModelAssembler.toCollectionModel(restaurantService.listAll());
    }

    @CheckSecurity.Restaurants.CanFind
    //@JsonView(RestaurantView.OnlyName.class)
    @Override
    @GetMapping(params = "projection=only-name")
    public CollectionModel<RestaurantOnlyNameModel> listAllOnlyName(){
        return restaurantOnlyNameModelAssembler.toCollectionModel(restaurantService.listAll());
    }

    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping("/{id}")
    public RestaurantModel findById(@PathVariable Long restaurantId){
            return restaurantModelAssembler.toModel(restaurantService.findById(restaurantId));
    }
    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping("/name-and-kitchen")
    public List<Restaurant> findByNameAndKitchen(String name, Long kitchenId){
        return restaurantService.findByNameAndKitchen(name, kitchenId);
    }

    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping("/name-and-freightRate")
    public List<Restaurant> findByNameAndFreightRate(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal){
        return restaurantService.findByNameAndFreightRate(name, freightRateInitial, freightRateFinal);
    }

    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping("/free-shipping")
    public List<Restaurant> withFreeShipping(String name){
        return restaurantService.withFreeShipping(name);
    }

    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping("/first")
    public ResponseEntity<?> findFirst(){
        try{
            return ResponseEntity.ok().body(restaurantService.findFirst());
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel save(@RequestBody @Valid RestaurantInput restaurantInput){
        try{
            Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);

            return restaurantModelAssembler.toModel(restaurantService.save(restaurant));
        } catch (KitchenNotFoundException | CityNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @Override
    @PutMapping("/{id}")
    public RestaurantModel update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput){
        try{
            Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);

            restaurantInputDisassembler.copyToDomainObject(restaurantInput, restaurant);

            return restaurantModelAssembler.toModel(restaurantService.save(restaurant));
        } catch (KitchenNotFoundException | CityNotFoundException e){
        throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @Override
    @PutMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activate(@PathVariable Long restaurantId){
        restaurantService.activate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @Override
    @PutMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long restaurantId){
        restaurantService.deactivate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @Override
    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void multipleActivation(@RequestBody List<Long> restaurantIds){
        try{
            restaurantService.activate(restaurantIds);
        } catch (RestaurantNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @Override
    @DeleteMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void multipleDeactivation(@RequestBody List<Long> restaurantIds){
        try{
            restaurantService.deactivate(restaurantIds);
        } catch (RestaurantNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @Override
    @PutMapping("/{id}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> opening(@PathVariable Long restaurantId){
        restaurantService.opening(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @Override
    @PutMapping("/{id}/closure")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> closure(@PathVariable Long restaurantId){
        restaurantService.closure(restaurantId);
        return ResponseEntity.noContent().build();
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
    public void delete(@PathVariable Long restaurantId){
        restaurantService.delete(restaurantId);
    }

}
