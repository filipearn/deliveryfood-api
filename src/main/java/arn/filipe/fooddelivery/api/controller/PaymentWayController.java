package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.PaymentWayInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.PaymentWayModelAssembler;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import arn.filipe.fooddelivery.api.model.input.PaymentWayInput;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.PaymentWayService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-ways")
public class PaymentWayController {

    @Autowired
    private PaymentWayService paymentWayService;

    @Autowired
    private PaymentWayInputDisassembler paymentWayInputDisassembler;

    @Autowired
    private PaymentWayModelAssembler paymentWayModelAssembler;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<PaymentWayModel> listAll(){
        return paymentWayModelAssembler.toCollectionModel(paymentWayService.listAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentWayModel save(@RequestBody @Valid PaymentWayInput paymentWayInput){
        PaymentWay paymentWay = paymentWayInputDisassembler.toDomainObject(paymentWayInput);

        return paymentWayModelAssembler.toModel(paymentWayService.save(paymentWay));
    }

    @PutMapping("/{id}")
    public PaymentWayModel update(@PathVariable Long id, @RequestBody @Valid PaymentWayInput paymentWayInput){
        PaymentWay paymentWay = paymentWayService.verifyIfExistsOrThrow(id);

        paymentWayInputDisassembler.copyToDomainObject(paymentWayInput, paymentWay);

        return paymentWayModelAssembler.toModel(paymentWayService.save(paymentWay));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        paymentWayService.delete(id);
    }

}
