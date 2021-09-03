package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.assembler.KitchenInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.KitchenModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.KitchenModel;
import arn.filipe.fooddelivery.api.v1.model.input.KitchenInput;
import arn.filipe.fooddelivery.api.v1.openapi.controller.KitchenControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.service.KitchenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
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

    @CheckSecurity.Kitchens.CanFind
    @Override
    @GetMapping
    public PagedModel<KitchenModel> listAll(@PageableDefault(size = 10) Pageable pageable){
        Page<Kitchen> kitchensPage = kitchenService.listAll(pageable);

        log.info("LISTAGEM DE COZINHA");

        PagedModel<KitchenModel> kitchensPagedModel = pagedResourcesAssembler
                .toModel(kitchensPage, kitchenModelAssembler);

        return kitchensPagedModel;
    }

    @CheckSecurity.Kitchens.CanFind
    @Override
    @GetMapping("/by-name")
    public CollectionModel<KitchenModel> findByNameContaining(String name){
        return kitchenModelAssembler.toCollectionModel(kitchenService.findByNameContaining(name));
    }

    @CheckSecurity.Kitchens.CanFind
    @Override
    @GetMapping("/{id}")
    public KitchenModel findById(@PathVariable Long id){
         return kitchenModelAssembler.toModel(kitchenService.findById(id));
    }

    //@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
    @CheckSecurity.Kitchens.CanEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel save(@RequestBody @Valid KitchenInput kitchenInput){
        Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);

        return kitchenModelAssembler.toModel(kitchenService.save(kitchen));
    }

    @CheckSecurity.Kitchens.CanEdit
    @Override
    @PutMapping("/{id}")
    public KitchenModel update(@PathVariable Long id, @RequestBody @Valid KitchenInput kitchenInput){
        Kitchen kitchen = kitchenService.verifyIfExistsOrThrow(id);

        kitchenInputDisassembler.copyToDomainObject(kitchenInput, kitchen);

        return kitchenModelAssembler.toModel(kitchenService.save(kitchen));
    }

    @CheckSecurity.Kitchens.CanEdit
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
            kitchenService.delete(id);
    }

}
