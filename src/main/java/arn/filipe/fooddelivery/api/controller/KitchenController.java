package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.KitchenInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.KitchenModelAssembler;
import arn.filipe.fooddelivery.api.model.KitchenModel;
import arn.filipe.fooddelivery.api.model.input.KitchenInput;
import arn.filipe.fooddelivery.api.openapi.controller.KitchenControllerOpenApi;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController implements KitchenControllerOpenApi {

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private KitchenInputDisassembler kitchenInputDisassembler;

    @Autowired
    private KitchenModelAssembler kitchenModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<KitchenModel> listAll(@PageableDefault(size = 10) Pageable pageable){
        Page<Kitchen> kitchensPage = kitchenService.listAll(pageable);

        PagedModel<KitchenModel> kitchensPagedModel = pagedResourcesAssembler
                .toModel(kitchensPage, kitchenModelAssembler);

        return kitchensPagedModel;
    }

    @GetMapping("/by-name")
    public CollectionModel<KitchenModel> findByNameContaining(String name){
        return kitchenModelAssembler.toCollectionModel(kitchenService.findByNameContaining(name));
    }

    @GetMapping("/{id}")
    public KitchenModel findById(@PathVariable Long id){
         return kitchenModelAssembler.toModel(kitchenService.findById(id));
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
