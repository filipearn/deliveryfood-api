package arn.filipe.fooddelivery.domain.event;

import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseOrderConfirmedEvent {

    private PurchaseOrder purchaseOrder;
}
