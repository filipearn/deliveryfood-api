package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.StateInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.StateModelAssembler;
import arn.filipe.fooddelivery.api.model.StateModel;
import arn.filipe.fooddelivery.api.model.input.StateInput;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.State;

import arn.filipe.fooddelivery.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @Autowired
    private StateInputDisassembler stateInputDisassembler;

    @Autowired
    private StateModelAssembler stateModelAssembler;

    @GetMapping
    public List<StateModel> listAll(){
        return stateModelAssembler.toCollectionModel(stateService.findAll());
    }

    @GetMapping("/{id}")
    public StateModel findById(@PathVariable Long id){
        return stateModelAssembler.toModel(stateService.findById(id));
    }

    @PostMapping
    public StateModel save(@RequestBody @Valid StateInput stateInput){
        State state = stateInputDisassembler.toDomainObject(stateInput);

        return stateModelAssembler.toModel(stateService.save(state));
    }

    @PutMapping("/{id}")
    public StateModel update(@PathVariable Long id, @RequestBody @Valid StateInput stateInput){
        State state = stateService.verifyIfExistsOrThrow(id);

        stateInputDisassembler.copyToDomainObject(stateInput, state);

        return stateModelAssembler.toModel(stateService.save(state));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
            stateService.delete(id);
    }

























}
