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
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<List<PaymentWayModel>> listAll(ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = paymentWayService.getLastUpdateDate();

        if(lastUpdateDate != null){
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        if(request.checkNotModified(eTag)){
            return null;
        }

        List<PaymentWayModel> paymentWaysModel =  paymentWayModelAssembler.toCollectionModel(paymentWayService.listAll());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(paymentWaysModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentWayModel> findById(@PathVariable Long id, ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = paymentWayService.getLastUpdateDateById(id);

        if(lastUpdateDate != null){
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        if(request.checkNotModified(eTag)){
            return null;
        }

        PaymentWayModel paymentWayModel = paymentWayModelAssembler.toModel(paymentWayService.findById(id));

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentWayModel);
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
