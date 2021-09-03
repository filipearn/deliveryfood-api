package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.openapi.controller.OrderFlowControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.domain.service.OrderFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/purchase-orders/{purchaseOrderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderFlowController implements OrderFlowControllerOpenApi {

    @Autowired
    private OrderFlowService orderFlowService;

    @CheckSecurity.PurchaseOrders.CanManage
    @Override
    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmation(@PathVariable String purchaseOrderCode){
            orderFlowService.confirm(purchaseOrderCode);

            return ResponseEntity.notFound().build();
    }

    @CheckSecurity.PurchaseOrders.CanManage
    @Override
    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delivery(@PathVariable String purchaseOrderCode){
        orderFlowService.delivery(purchaseOrderCode);

        return ResponseEntity.notFound().build();
    }

    @CheckSecurity.PurchaseOrders.CanManage
    @Override
    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancellation(@PathVariable String purchaseOrderCode){
        orderFlowService.cancel(purchaseOrderCode);

        return ResponseEntity.notFound().build();
    }
}
