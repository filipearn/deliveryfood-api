package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kitchens")
public class KitchenController {

    @Autowired
    private KitchenService kitchenService;

    @GetMapping
    public List<Kitchen> listAll(){
        return kitchenService.listAll();
    }

    @GetMapping("/{id}")
    public Kitchen findById(@PathVariable Long id){
         return kitchenService.findById(id);
    }

    @GetMapping("/by-name")
    public List<Kitchen> findByNameContaining(String name){
        return kitchenService.findByNameContaining(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen save(@RequestBody Kitchen kitchen){
        return kitchenService.save(kitchen);
    }

    @PutMapping("/{id}")
    public Kitchen update(@PathVariable Long id, @RequestBody Kitchen kitchen){
            return kitchenService.update(id, kitchen);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
            kitchenService.delete(id);
    }

}
