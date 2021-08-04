package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.model.State;
import arn.filipe.fooddelivery.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    public List<State> listAll(){
        return stateRepository.findAll();
    }
}
