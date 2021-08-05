package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class KitchenService {

    @Autowired
    private KitchenRepository kitchenRepository;

    public Kitchen save(Kitchen kitchen){
        return kitchenRepository.save(kitchen);
    }

    public List<Kitchen> listAll(){
        return kitchenRepository.findAll();
    }

    public Kitchen findById(Long id){
        return kitchenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Kitchen with id %d not found.", id)));
    }

    public Kitchen update(Long id, Kitchen kitchen){
        Kitchen kitchenToUpdate = kitchenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Kitchen with id %d not found.", id)));

        BeanUtils.copyProperties(kitchen, kitchenToUpdate, "id");
        return kitchenRepository.save(kitchenToUpdate);
    }


    public void delete(Long id) {
        kitchenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Kitchen with id %d not found.", id)));

        try {
            kitchenRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Kitchen with id %d can't be removed. Resource in use.", id));
        }
    }
}
