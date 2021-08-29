package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.assembler.StateInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.StateModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.StateModel;
import arn.filipe.fooddelivery.api.v1.model.input.StateInput;
import arn.filipe.fooddelivery.api.v1.openapi.controller.StateControllerOpenApi;
import arn.filipe.fooddelivery.domain.model.State;

import arn.filipe.fooddelivery.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerOpenApi {

    @Autowired
    private StateService stateService;

    @Autowired
    private StateInputDisassembler stateInputDisassembler;

    @Autowired
    private StateModelAssembler stateModelAssembler;

    @GetMapping
    public CollectionModel<StateModel> listAll(){
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
