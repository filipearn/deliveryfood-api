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
        return stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("State with id %d not found.", id)));
    }

    public State update(Long id, State state) {
        State stateToUpdate = stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("State with id %d not found.", id)));

        BeanUtils.copyProperties(state, stateToUpdate, "id");

        return stateRepository.save(stateToUpdate);
    }

    public void delete(Long id) {
        stateRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException(
                        String.format("State with id %d not found.", id)));
        try {
            stateRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("State with id %d can't be removed. Resource in use.", id));
        }
    }

























}
