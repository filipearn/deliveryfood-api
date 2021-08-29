package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.PurchaseOrderController;
import arn.filipe.fooddelivery.api.controller.RestaurantController;
import arn.filipe.fooddelivery.api.controller.UserController;
import arn.filipe.fooddelivery.api.model.PurchaseOrderSummaryModel;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PurchaseOrderSummaryModelAssembler extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderSummaryModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public PurchaseOrderSummaryModelAssembler(){
        super(PurchaseOrderController.class, PurchaseOrderSummaryModel.class);
    }

    public PurchaseOrderSummaryModel toModel(PurchaseOrder purchaseOrder){
        PurchaseOrderSummaryModel purchaseOrderSummaryModel = createModelWithId(purchaseOrder.getId(), purchaseOrder);

        modelMapper.map(purchaseOrder, purchaseOrderSummaryModel);

        purchaseOrderSummaryModel.add(buildLinks.linkToPurchaseOrder(purchaseOrder.getId()));

        //Link to restaurant
        purchaseOrderSummaryModel.getRestaurant().add(buildLinks.linkToRestaurant(purchaseOrderSummaryModel.getRestaurant().getId()));

        //Link to user client
        purchaseOrderSummaryModel.getClient().add(buildLinks.linkToUser(purchaseOrderSummaryModel.getClient().getId()));

        return purchaseOrderSummaryModel;
    }

    @Override
    public CollectionModel<PurchaseOrderSummaryModel> toCollectionModel(Iterable<? extends PurchaseOrder> entities) {
        return super.toCollectionModel(entities)
                .add(buildLinks.linkToPurchaseOrder());
    }
}
