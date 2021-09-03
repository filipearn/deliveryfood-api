package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.ResourceUriHelper;
import arn.filipe.fooddelivery.api.v1.assembler.CityModelAssembler;
import arn.filipe.fooddelivery.api.v1.assembler.CityInputDisassembler;
import arn.filipe.fooddelivery.api.v1.openapi.controller.CityControllerOpenApi;
import arn.filipe.fooddelivery.api.v1.model.CityModel;
import arn.filipe.fooddelivery.api.v1.model.input.CityInput;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.core.web.FoodDeliveryMediaTypes;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.StateNotFoundException;
import arn.filipe.fooddelivery.domain.model.City;
import arn.filipe.fooddelivery.domain.service.CityService;
import arn.filipe.fooddelivery.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping(path = "/api/v1/cities", produces = FoodDeliveryMediaTypes.V1_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/api/v1/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityInputDisassembler cityInputDisassembler;

    @Autowired
    private CityModelAssembler cityInputAssembler;

    @Autowired
    private StateService stateService;

    @CheckSecurity.Cities.CanFind
    @Override
    @GetMapping
    public CollectionModel<CityModel> listAll(){
        return cityInputAssembler.toCollectionModel(cityService.listAll());
    }

    @CheckSecurity.Cities.CanFind
    @Override
    @GetMapping("/{id}")
    public CityModel findById(@PathVariable Long cityId){
        return cityInputAssembler.toModel(cityService.findById(cityId));
    }

    @CheckSecurity.Cities.CanEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel save(@RequestBody @Valid CityInput cityInput){
        try{
            City city = cityInputDisassembler.toDomainObject(cityInput);

            CityModel cityModel = cityInputAssembler.toModel(cityService.save(city));

            ResourceUriHelper.addUriInResponseHeader(cityModel.getId());

            return cityModel;
        } catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cities.CanEdit
    @Override
    @PutMapping("/{id}")
    public CityModel update(@PathVariable Long cityId,
                            @RequestBody @Valid CityInput cityInput){
        try{
            City city = cityService.verifyIfExistsOrThrow(cityId);

            cityInputDisassembler.copyToDomainObject(cityInput, city);

            city = cityService.save(city);

            return cityInputAssembler.toModel(city);
        } catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cities.CanEdit
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId){
            cityService.delete(cityId);
    }




















}
