package arn.filipe.fooddelivery.domain.listener;

import arn.filipe.fooddelivery.domain.event.PurchaseOrderConfirmedEvent;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import arn.filipe.fooddelivery.domain.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ClientNotificationOrderConfirmedListener {

    @Autowired
    private SendEmailService sendEmailService;

    @TransactionalEventListener
    public void whenOrderConfirmed(PurchaseOrderConfirmedEvent event){

        PurchaseOrder purchaseOrder = event.getPurchaseOrder();

        var message = SendEmailService.Message.builder()
                        .subject(purchaseOrder.getRestaurant().getName()+ " - Order confirmed")
                        .body("PurchasedOrderConfirmed.html")
                        .recipient(purchaseOrder.getClient().getEmail())
                        .variable("purchaseOrder", purchaseOrder)
                        .build();

        sendEmailService.send(message);
    }
}
