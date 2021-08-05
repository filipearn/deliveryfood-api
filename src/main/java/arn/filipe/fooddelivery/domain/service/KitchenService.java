package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
        Optional<Kitchen> kitchen = kitchenRepository.findById(id);

        if(kitchen.isPresent()){
            return kitchen.get();
        }
        else
        {
            return null;
        }
    }

    public Kitchen update(Long id, Kitchen kitchen){
        Optional<Kitchen> kitchenToUpdate = kitchenRepository.findById(id);
        if(kitchenToUpdate.isPresent()){
            BeanUtils.copyProperties(kitchen, kitchenToUpdate.get(), "id");
            return kitchenRepository.save(kitchenToUpdate.get());
        }
        return null;
    }


    public void delete(Long id) {
        if (kitchenRepository.findById(id).isPresent()) {
            try {
                kitchenRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new EntityInUseException(
                        String.format("Kitchen with id %d can't be removed. Resource in use.", id));
            }
        }
        else{
            throw new EntityNotFoundException(
                    String.format("Kitchen with id %d not found.", id));
        }
    }
}
