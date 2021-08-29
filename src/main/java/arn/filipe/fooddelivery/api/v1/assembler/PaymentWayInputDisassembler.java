package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.model.input.PaymentWayInput;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentWayInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public PaymentWay toDomainObject(PaymentWayInput paymentWayInput){
        return modelMapper.map(paymentWayInput, PaymentWay.class);
    }

    public void copyToDomainObject(PaymentWayInput paymentWayInput, PaymentWay paymentWay){
        modelMapper.map(paymentWayInput, paymentWay);
    }
}
