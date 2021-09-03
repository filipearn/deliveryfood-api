package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.model.PurchaseOrderModel;
import arn.filipe.fooddelivery.api.v1.controller.PurchaseOrderController;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderModelAssembler extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    public PurchaseOrderModelAssembler(){
        super(PurchaseOrderController.class, PurchaseOrderModel.class);
    }

    @Override
    public PurchaseOrderModel toModel(PurchaseOrder purchaseOrder){
        PurchaseOrderModel purchaseOrderModel = createModelWithId(purchaseOrder.getCode(), purchaseOrder);

        modelMapper.map(purchaseOrder, purchaseOrderModel);

        purchaseOrderModel.add(buildLinks.linkToPurchaseOrder("purchaseOrders"));

        if(security.canManagePurchaseOrders(purchaseOrder.getCode())){
            if(purchaseOrder.canBeConfirmed())
            {
                purchaseOrderModel.add(buildLinks.linkToPurchaseOrderConfirmation(purchaseOrder.getCode(), "confirm"));
            }

            if(purchaseOrder.canBeCancelled()){
                purchaseOrderModel.add(buildLinks.linkToPurchaseOrderCancellation(purchaseOrder.getCode(), "cancel"));
            }

            if(purchaseOrder.canBeDelivered()){
                purchaseOrderModel.add(buildLinks.linkToPurchaseOrderDelivery(purchaseOrder.getCode(), "delivery"));
            }
        }


        //Link to restaurant
        purchaseOrderModel.getRestaurant().add(buildLinks.linkToRestaurant(purchaseOrder.getRestaurant().getId()));

        //Link to payment way
        purchaseOrderModel.getPaymentWay().add(buildLinks.linkToPaymentWay(purchaseOrderModel.getPaymentWay().getId()));

        //Link to user client
        purchaseOrderModel.getClient().add(buildLinks.linkToUser(purchaseOrderModel.getClient().getId()));

        //Link to each item
        purchaseOrderModel.getItems().stream()
                .forEach(item ->
                        item.add(buildLinks.linkToRestaurantProducts(purchaseOrderModel.getRestaurant().getId(), item.getProductId(), "product")));

        //Link to city
        purchaseOrderModel.getAddress().getCity().add(buildLinks.linkToCity(purchaseOrder.getAddress().getCity().getId()));

        return purchaseOrderModel;
    }

}
