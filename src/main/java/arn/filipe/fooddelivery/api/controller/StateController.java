package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.State;

import arn.filipe.fooddelivery.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public List<State> listAll(){
        return stateService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable Long id){
        State state = stateService.findById(id);

        if(state != null){
            return ResponseEntity.ok().body(state);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<State> save(@RequestBody State state){
        state = stateService.save(state);

        return ResponseEntity.status(HttpStatus.CREATED).body(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state){
        state = stateService.update(id, state);

        if(state != null){
            return ResponseEntity.ok().body(state);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            stateService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

























}
