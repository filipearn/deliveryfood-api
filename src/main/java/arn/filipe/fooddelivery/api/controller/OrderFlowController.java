package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.service.OrderFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase-orders/{purchaseOrderCode}")
public class OrderFlowController {

    @Autowired
    private OrderFlowService orderFlowService;

    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmation(@PathVariable String purchaseOrderCode){
            orderFlowService.confirm(purchaseOrderCode);
    }

    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivery(@PathVariable String purchaseOrderCode){
        orderFlowService.delivery(purchaseOrderCode);
    }

    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellation(@PathVariable String purchaseOrderCode){
        orderFlowService.cancel(purchaseOrderCode);
    }
}
