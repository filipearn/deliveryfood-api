package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.PurchaseOrderController;
import arn.filipe.fooddelivery.api.v1.model.PurchaseOrderSummaryModel;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PurchaseOrderSummaryModelAssembler extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderSummaryModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    public PurchaseOrderSummaryModelAssembler(){
        super(PurchaseOrderController.class, PurchaseOrderSummaryModel.class);
    }

    public PurchaseOrderSummaryModel toModel(PurchaseOrder purchaseOrder){
        PurchaseOrderSummaryModel purchaseOrderSummaryModel = createModelWithId(purchaseOrder.getId(), purchaseOrder);

        modelMapper.map(purchaseOrder, purchaseOrderSummaryModel);

        if(security.canFindPurchaseOrders()){
            purchaseOrderSummaryModel.add(buildLinks.linkToPurchaseOrder(purchaseOrder.getCode()));
        }

        //Link to restaurant
        if(security.canFindRestaurants()){
            purchaseOrderSummaryModel.getRestaurant().add(buildLinks.linkToRestaurant(purchaseOrderSummaryModel.getRestaurant().getId()));
        }

        //Link to user client
        if(security.canFindUsersTeamsPermissions()){
            purchaseOrderSummaryModel.getClient().add(buildLinks.linkToUser(purchaseOrderSummaryModel.getClient().getId()));
        }

        return purchaseOrderSummaryModel;
    }

    @Override
    public CollectionModel<PurchaseOrderSummaryModel> toCollectionModel(Iterable<? extends PurchaseOrder> entities) {
        return super.toCollectionModel(entities)
                .add(buildLinks.linkToPurchaseOrder());
    }
}
