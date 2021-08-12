package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.City;
import arn.filipe.fooddelivery.domain.model.State;
import arn.filipe.fooddelivery.domain.repository.CityRepository;
import arn.filipe.fooddelivery.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    public static final String CITY_NOT_FOUND = "City with id %d not found.";
    public static final String CITY_IN_USE = "City with id %d can't be removed. Resource in use.";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateService stateService;

    public List<City> listAll(){
        return cityRepository.findAll();
    }

    public City findById(Long id){
        return verifyIfExistsOrThrow(id);
    }

    public City save(City city){
        Long stateId = city.getState().getId();

        State state = stateService.findById(stateId);

        city.setState(state);

        return cityRepository.save(city);
    }

    public City update(Long id, City city){
        Long stateId = city.getState().getId();
        State state = stateService.findById(stateId);

        City cityToUpdate = verifyIfExistsOrThrow(id);

        city.setState(state);

        BeanUtils.copyProperties(city, cityToUpdate, "id");

        return cityRepository.save(cityToUpdate);
    }

    public void delete(Long id){
        try {
            cityRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format(CITY_NOT_FOUND, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(CITY_IN_USE, id));
        }
    }

    private City verifyIfExistsOrThrow(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CITY_NOT_FOUND, id)));
    }
























}
