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

    public static final String KITCHEN_NOT_FOUND = "Kitchen with id %d not found.";
    public static final String KITCHEN_IN_USE = "Kitchen with id %d can't be removed. Resource in use.";

    @Autowired
    private KitchenRepository kitchenRepository;

    public Kitchen save(Kitchen kitchen){
        return kitchenRepository.save(kitchen);
    }

    public List<Kitchen> listAll(){
        return kitchenRepository.findAll();
    }

    public Kitchen findById(Long id){
        return verifyIfExistsOrThrow(id);
    }



    public Kitchen update(Long id, Kitchen kitchen){
        Kitchen kitchenToUpdate = verifyIfExistsOrThrow(id);

        BeanUtils.copyProperties(kitchen, kitchenToUpdate, "id");
        return kitchenRepository.save(kitchenToUpdate);
    }


    public void delete(Long id) {
        try {
            kitchenRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format(KITCHEN_NOT_FOUND, id));
        }
        catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(KITCHEN_IN_USE, id));
        }
    }

    public List<Kitchen> findByName(String name) {
        return kitchenRepository.findByName(name);
    }

    public List<Kitchen> findByNameContaining(String name) {
        return kitchenRepository.findByNameContaining(name);
    }

    private Kitchen verifyIfExistsOrThrow(Long id) {
        return kitchenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(KITCHEN_NOT_FOUND, id)));
    }
}
