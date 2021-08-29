package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.PaymentWayController;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
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

    public PaymentWayModelAssembler(){
        super(PaymentWayController.class, PaymentWayModel.class);
    }

    public PaymentWayModel toModel(PaymentWay paymentWay){
        PaymentWayModel paymentWayModel = createModelWithId(paymentWay.getId(), paymentWay);

        modelMapper.map(paymentWay, paymentWayModel);

        paymentWayModel.add(buildLinks.linkToRestaurantPaymentWay("paymentWay"));

        return paymentWayModel;
    }

    @Override
    public CollectionModel<PaymentWayModel> toCollectionModel(Iterable<? extends PaymentWay> entities) {
        return super.toCollectionModel(entities)
                .add(buildLinks.linkToPaymentWay());
    }
}
