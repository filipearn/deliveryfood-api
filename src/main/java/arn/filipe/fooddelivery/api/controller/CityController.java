package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.ResourceUriHelper;
import arn.filipe.fooddelivery.api.assembler.CityModelAssembler;
import arn.filipe.fooddelivery.api.assembler.CityInputDisassembler;
import arn.filipe.fooddelivery.api.openapi.controller.CityControllerOpenApi;
import arn.filipe.fooddelivery.api.model.CityModel;
import arn.filipe.fooddelivery.api.model.input.CityInput;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.StateNotFoundException;
import arn.filipe.fooddelivery.domain.model.City;
import arn.filipe.fooddelivery.domain.service.CityService;
import arn.filipe.fooddelivery.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
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

    @GetMapping
    public CollectionModel<CityModel> listAll(){
        return cityInputAssembler.toCollectionModel(cityService.listAll());
    }

    @GetMapping("/{id}")
    public CityModel findById(@PathVariable Long id){
        return cityInputAssembler.toModel(cityService.findById(id));
    }

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

    @PutMapping("/{id}")
    public CityModel update(@PathVariable Long id,
                            @RequestBody @Valid CityInput cityInput){
        try{
            City city = cityService.verifyIfExistsOrThrow(id);

            cityInputDisassembler.copyToDomainObject(cityInput, city);

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
