package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.PaymentWayInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.PaymentWayModelAssembler;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.PaymentWayService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/payment-ways")
public class RestaurantPaymentWayController {
    @Autowired
    private PaymentWayService paymentWayService;

    @Autowired
    private PaymentWayInputDisassembler paymentWayInputDisassembler;

    @Autowired
    private PaymentWayModelAssembler paymentWayModelAssembler;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<PaymentWayModel> listAll(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);
        return paymentWayModelAssembler.toCollectionModel(restaurant.getPaymentWays());
    }

    @PutMapping("/{paymentWayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long restaurantId, @PathVariable Long paymentWayId){

        restaurantService.associatePaymentWay(restaurantId, paymentWayId);
    }

    @DeleteMapping("/{paymentWayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentWayId){

        restaurantService.disassociatePaymentWay(restaurantId, paymentWayId);
    }
}
