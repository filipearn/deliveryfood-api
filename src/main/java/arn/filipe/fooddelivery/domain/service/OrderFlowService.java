package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.enums.OrderStatus;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class OrderFlowService {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Transactional
    public void confirm(Long purchaseOrderId){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderId);
        purchaseOrder.confirm();
    }

    @Transactional
    public void cancel(Long purchaseOrderId){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderId);
        purchaseOrder.cancel();
    }

    @Transactional
    public void delivery(Long purchaseOrderId){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderId);
        purchaseOrder.delivery();
    }
}
