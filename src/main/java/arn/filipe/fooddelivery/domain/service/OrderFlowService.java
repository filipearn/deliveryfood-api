package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import arn.filipe.fooddelivery.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class OrderFlowService {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public void confirm(String purchaseOrderCode){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderCode);
        purchaseOrder.confirm();

        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void cancel(String purchaseOrderCode){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderCode);
        purchaseOrder.cancel();

        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void delivery(String purchaseOrderCode){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderCode);
        purchaseOrder.delivery();
    }
}
