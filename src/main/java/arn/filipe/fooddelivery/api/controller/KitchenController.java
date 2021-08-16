package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.KitchenInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.KitchenModelAssembler;
import arn.filipe.fooddelivery.api.model.KitchenModel;
import arn.filipe.fooddelivery.api.model.input.KitchenInput;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kitchens")
public class KitchenController {

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private KitchenInputDisassembler kitchenInputDisassembler;

    @Autowired
    private KitchenModelAssembler kitchenModelAssembler;

    @GetMapping
    public List<KitchenModel> listAll(){
        return kitchenModelAssembler.toCollectionModel(kitchenService.listAll());
    }

    @GetMapping("/{id}")
    public KitchenModel findById(@PathVariable Long id){
         return kitchenModelAssembler.toModel(kitchenService.findById(id));
    }

    @GetMapping("/by-name")
    public List<KitchenModel> findByNameContaining(String name){
        return kitchenModelAssembler.toCollectionModel(kitchenService.findByNameContaining(name));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel save(@RequestBody @Valid KitchenInput kitchenInput){
        Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);

        return kitchenModelAssembler.toModel(kitchenService.save(kitchen));
    }

    @PutMapping("/{id}")
    public KitchenModel update(@PathVariable Long id, @RequestBody @Valid KitchenInput kitchenInput){
        Kitchen kitchen = kitchenService.verifyIfExistsOrThrow(id);

        kitchenInputDisassembler.copyToDomainObject(kitchenInput, kitchen);

        return kitchenModelAssembler.toModel(kitchenService.save(kitchen));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
            kitchenService.delete(id);
    }

}
