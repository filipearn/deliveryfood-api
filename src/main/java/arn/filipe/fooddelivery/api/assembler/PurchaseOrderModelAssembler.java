package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.PurchaseOrderModel;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PurchaseOrderModel toModel(PurchaseOrder purchaseOrder){
        return modelMapper.map(purchaseOrder, PurchaseOrderModel.class);
    }

    public List<PurchaseOrderModel> toCollectionModel(Collection<PurchaseOrder> purchaseOrders){
        return purchaseOrders.stream()
                .map(purchaseOrder -> toModel(purchaseOrder))
                .collect(Collectors.toList());
    }
}
