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
        Optional<City> city = cityRepository.findById(id);

        return city.orElse(null);
    }

    public City save(City city){
        Long stateId = city.getState().getId();
        Optional<State> state = stateRepository.findById(stateId);

        if(state.isEmpty()){
            throw new EntityNotFoundException(
                    String.format("State with id %d not found.", stateId));
        }

        return cityRepository.save(city);
    }

    public City update(Long id, City city){
        Optional<City> cityToUpdate = cityRepository.findById(id);
        Long stateId = city.getState().getId();
        Optional<State> state = stateRepository.findById(stateId);

        if(state.isEmpty()){
            throw new EntityNotFoundException(
                    String.format("State with id %d not found.", stateId));
        }

        if(cityToUpdate.isPresent()){
            BeanUtils.copyProperties(city, cityToUpdate.get(), "id");
            return cityRepository.save(cityToUpdate.get());
        }
        return null;

    }

    public void delete(Long id){
        if (cityRepository.findById(id).isPresent()) {
            try {
                cityRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new EntityInUseException(
                        String.format("City with id %d can't be removed. Resource in use.", id));
            }
        }
        else{
            throw new EntityNotFoundException(
                    String.format("City with id %d not found.", id));
        }

    }
























}
