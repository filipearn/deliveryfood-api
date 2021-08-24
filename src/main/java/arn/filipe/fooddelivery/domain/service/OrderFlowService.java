package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class OrderFlowService {

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Transactional
    public void confirm(String purchaseOrderCode){
        PurchaseOrder purchaseOrder = purchaseOrderService.verifyIfExistsOrThrow(purchaseOrderCode);
        purchaseOrder.confirm();

        var message = SendEmailService.Message.builder()
                        .subject(purchaseOrder.getRestaurant().getName()+ " - Order confirmed")
                        .body("The order with code <strong>" +
                                purchaseOrder.getCode() + " </strong> was confirmed!")
                        .recipient(purchaseOrder.getClient().getEmail())
                        .recipient("monografias@ufop.edu.br")
                        .build();

        sendEmailService.send(message);
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
