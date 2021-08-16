package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.KitchenModel;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentWayModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PaymentWayModel toModel(PaymentWay paymentWay){
        return modelMapper.map(paymentWay, PaymentWayModel.class);
    }

    public List<PaymentWayModel> toCollectionModel(List<PaymentWay> paymentWays){
        return paymentWays.stream()
                .map(paymentWay -> toModel(paymentWay))
                .collect(Collectors.toList());
    }
}
