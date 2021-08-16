package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.api.assembler.KitchenModelAssembler;
import arn.filipe.fooddelivery.api.model.KitchenModel;
import arn.filipe.fooddelivery.api.model.input.KitchenInput;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.KitchenNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class KitchenService {

    public static final String KITCHEN_IN_USE = "Kitchen with id %d can't be removed. Resource in use.";

    @Autowired
    private KitchenRepository kitchenRepository;

    @Transactional
    public Kitchen save(Kitchen kitchen){
        return kitchenRepository.save(kitchen);
    }

    public List<Kitchen> listAll(){
        return kitchenRepository.findAll();
    }

    public Kitchen findById(Long id){
        return verifyIfExistsOrThrow(id);
    }


    @Transactional
    public Kitchen update(Long id, Kitchen kitchen){
        Kitchen kitchenToUpdate = verifyIfExistsOrThrow(id);

        BeanUtils.copyProperties(kitchen, kitchenToUpdate, "id");
        return kitchenRepository.save(kitchenToUpdate);
    }

    @Transactional
    public void delete(Long id) {
        try {
            kitchenRepository.deleteById(id);
            kitchenRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new KitchenNotFoundException(id);
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

    public Kitchen verifyIfExistsOrThrow(Long id) {
        return kitchenRepository.findById(id)
                .orElseThrow(() -> new KitchenNotFoundException(id));
    }
}
