package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.model.input.PurchaseOrderInput;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public PurchaseOrder toDomainObject(PurchaseOrderInput purchaseOrderInput){
        return modelMapper.map(purchaseOrderInput, PurchaseOrder.class);
    }

    public void copyToDomainObject(PurchaseOrderInput purchaseOrderInput, PurchaseOrder purchaseOrder){
        modelMapper.map(purchaseOrderInput, purchaseOrder);
    }
}
