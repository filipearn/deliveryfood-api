package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> findById(@PathVariable Long id){
        Kitchen kitchen = kitchenService.findById(id);

        if(kitchen != null){
            return ResponseEntity.ok().body(kitchen);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Kitchen save(@RequestBody Kitchen kitchen){
        return kitchenService.save(kitchen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen){
        kitchen = kitchenService.update(id, kitchen);

        if(kitchen != null){
            return ResponseEntity.ok().body(kitchen);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long id){
        try{
            kitchenService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}
