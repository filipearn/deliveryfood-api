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
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    public List<City> listAll(){
        return cityRepository.findAll();
    }

    public City findById(Long id){
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("City with id %d not found.", id)));
    }

    public City save(City city){
        Long stateId = city.getState().getId();
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("State with id %d not found.", stateId)));

        city.setState(state);

        return cityRepository.save(city);
    }

    public City update(Long id, City city){
        Long stateId = city.getState().getId();
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("State with id %d not found.", stateId)));

        City cityToUpdate = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("City with id %d not found.", id)));

        city.setState(state);

        BeanUtils.copyProperties(city, cityToUpdate, "id");

        return cityRepository.save(cityToUpdate);
    }

    public void delete(Long id){
        cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("City with id %d not found.", id)));

        try {
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("City with id %d can't be removed. Resource in use.", id));
        }
    }
























}
