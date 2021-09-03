package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.KitchenController;
import arn.filipe.fooddelivery.api.v1.model.KitchenModel;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class KitchenModelAssembler extends RepresentationModelAssemblerSupport<Kitchen, KitchenModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    public KitchenModelAssembler(){
        super(KitchenController.class, KitchenModel.class);
    }

    @Override
    public KitchenModel toModel(Kitchen kitchen){
        KitchenModel kitchenModel = createModelWithId(kitchen.getId(), kitchen);

        modelMapper.map(kitchen, kitchenModel);

        if(security.canFindKitchens()){
            kitchenModel.add(buildLinks.linkToKitchen("kitchens"));
        }

        return kitchenModel;
    }

    @Override
    public CollectionModel<KitchenModel> toCollectionModel(Iterable<? extends Kitchen> entities) {
        CollectionModel<KitchenModel> collectionModel =super.toCollectionModel(entities);

        if(security.canFindKitchens()){
            collectionModel.add(linkTo(KitchenController.class).withSelfRel());
        }

        return collectionModel;
    }
}
