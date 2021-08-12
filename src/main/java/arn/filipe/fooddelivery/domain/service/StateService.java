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

    public static final String STATE_NOT_FOUND = "State with id %d not found.";
    public static final String STATE_IN_USE = "State with id %d can't be removed. Resource in use.";
    @Autowired
    private StateRepository stateRepository;

    public State save(State state) {
        return stateRepository.save(state);
    }

    public List<State> findAll() {
        return stateRepository.findAll();
    }

    public State findById(Long id) {
        return verifyIfExistsOrThrow(id);
    }

    public State update(Long id, State state) {
        State stateToUpdate = verifyIfExistsOrThrow(id);

        BeanUtils.copyProperties(state, stateToUpdate, "id");

        return stateRepository.save(stateToUpdate);
    }

    public void delete(Long id) {
        try {
            stateRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format(STATE_NOT_FOUND, id));
        }catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(STATE_IN_USE, id));
        }
    }

    private State verifyIfExistsOrThrow(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(STATE_NOT_FOUND, id)));
    }

























}
