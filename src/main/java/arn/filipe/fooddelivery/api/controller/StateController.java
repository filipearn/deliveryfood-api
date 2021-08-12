package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
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
    public State findById(@PathVariable Long id){
        return stateService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State save(@RequestBody State state){
        return stateService.save(state);
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @RequestBody State state){
        return stateService.update(id, state);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
            stateService.delete(id);
    }

























}
