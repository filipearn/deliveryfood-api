package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderFlowService {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Transactional
    public void confirm(String purchaseOrderCode){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderCode);
        purchaseOrder.confirm();
    }

    @Transactional
    public void cancel(String purchaseOrderCode){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderCode);
        purchaseOrder.cancel();
    }

    @Transactional
    public void delivery(String purchaseOrderCode){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderCode);
        purchaseOrder.delivery();
    }
}
