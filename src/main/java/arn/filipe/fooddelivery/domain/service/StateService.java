package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.State;
import arn.filipe.fooddelivery.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public State save(State state) {
        return stateRepository.save(state);
    }

    public List<State> findAll() {
        return stateRepository.findAll();
    }

    public State findById(Long id) {
        Optional<State> state =  stateRepository.findById(id);

        if(state.isPresent()){
            return state.get();
        }
        else{
            return null;
        }
    }

    public State update(Long id, State state) {
        Optional<State> stateToUpdate = stateRepository.findById(id);

        if(stateToUpdate.isPresent()){
            BeanUtils.copyProperties(state, stateToUpdate.get(), "id");
            return stateRepository.save(stateToUpdate.get());
        }
        else{
            return null;
        }
    }

    public void delete(Long id) {
        if (stateRepository.findById(id).isPresent()) {
            try {
                stateRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new EntityInUseException(
                        String.format("State with id %d can't be removed. Resource in use.", id));
            }
        }
        else{
            throw new EntityNotFoundException(
                    String.format("State with id %d not found.", id));
        }
    }

























}
