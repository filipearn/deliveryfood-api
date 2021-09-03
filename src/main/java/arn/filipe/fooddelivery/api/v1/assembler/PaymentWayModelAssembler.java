package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.PaymentWayController;
import arn.filipe.fooddelivery.api.v1.model.PaymentWayModel;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class PaymentWayModelAssembler extends RepresentationModelAssemblerSupport<PaymentWay, PaymentWayModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    public PaymentWayModelAssembler(){
        super(PaymentWayController.class, PaymentWayModel.class);
    }

    public PaymentWayModel toModel(PaymentWay paymentWay){
        PaymentWayModel paymentWayModel = createModelWithId(paymentWay.getId(), paymentWay);

        modelMapper.map(paymentWay, paymentWayModel);

        if(security.canFindPaymentWays()){
            paymentWayModel.add(buildLinks.linkToRestaurantPaymentWay("paymentWay"));
        }


        return paymentWayModel;
    }

    @Override
    public CollectionModel<PaymentWayModel> toCollectionModel(Iterable<? extends PaymentWay> entities) {
        CollectionModel<PaymentWayModel> collectionModel = super.toCollectionModel(entities);

        if(security.canFindPaymentWays()){
            collectionModel.add(buildLinks.linkToPaymentWay());
        }

        return collectionModel;
    }
}
