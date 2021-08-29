package arn.filipe.fooddelivery.api.v2.controller;

import arn.filipe.fooddelivery.api.ResourceUriHelper;

import arn.filipe.fooddelivery.api.v1.model.CityModel;
import arn.filipe.fooddelivery.api.v2.assembler.CityInputDisassemblerV2;
import arn.filipe.fooddelivery.api.v2.assembler.CityModelAssemblerV2;
import arn.filipe.fooddelivery.api.v2.model.CityModelV2;
import arn.filipe.fooddelivery.api.v2.model.input.CityInputV2;
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

//@RequestMapping(path = "/api/v1/cities", produces = FoodDeliveryMediaTypes.V2_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/api/v2/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityControllerV2 {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityInputDisassemblerV2 cityInputDisassembler;

    @Autowired
    private CityModelAssemblerV2 cityInputAssembler;

    @Autowired
    private StateService stateService;

    @GetMapping
    public CollectionModel<CityModelV2> listAll(){
        return cityInputAssembler.toCollectionModel(cityService.listAll());
    }

    @GetMapping("/{id}")
    public CityModelV2 findById(@PathVariable Long id){
        return cityInputAssembler.toModel(cityService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModelV2 save(@RequestBody @Valid CityInputV2 cityInputV2){
        try{
            City city = cityInputDisassembler.toDomainObject(cityInputV2);

            CityModelV2 cityModel = cityInputAssembler.toModel(cityService.save(city));

            ResourceUriHelper.addUriInResponseHeader(cityModel.getCityId());

            return cityModel;
        } catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CityModelV2 update(@PathVariable Long id,
                            @RequestBody @Valid CityInputV2 cityInputV2){
        try{
            City city = cityService.verifyIfExistsOrThrow(id);

            cityInputDisassembler.copyToDomainObject(cityInputV2, city);

            city = cityService.save(city);

            return cityInputAssembler.toModel(city);
        } catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
            cityService.delete(id);
    }




















}
