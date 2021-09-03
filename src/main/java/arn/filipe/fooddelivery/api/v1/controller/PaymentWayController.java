package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.assembler.PaymentWayInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.PaymentWayModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.PaymentWayModel;
import arn.filipe.fooddelivery.api.v1.model.input.PaymentWayInput;
import arn.filipe.fooddelivery.api.v1.openapi.controller.PaymentWayControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import arn.filipe.fooddelivery.domain.service.PaymentWayService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/api/v1/payment-ways", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentWayController implements PaymentWayControllerOpenApi {

    @Autowired
    private PaymentWayService paymentWayService;

    @Autowired
    private PaymentWayInputDisassembler paymentWayInputDisassembler;

    @Autowired
    private PaymentWayModelAssembler paymentWayModelAssembler;

    @Autowired
    private RestaurantService restaurantService;

    @CheckSecurity.PaymentWays.CanFind
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<PaymentWayModel>> listAll(ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = paymentWayService.getLastUpdateDate();

        if(lastUpdateDate != null){
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        if(request.checkNotModified(eTag)){
            return null;
        }

        CollectionModel<PaymentWayModel> paymentWaysModel =  paymentWayModelAssembler.toCollectionModel(paymentWayService.listAll());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(paymentWaysModel);
    }

    @CheckSecurity.PaymentWays.CanFind
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PaymentWayModel> findById(@PathVariable Long paymentWayId, ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = paymentWayService.getLastUpdateDateById(paymentWayId);

        if(lastUpdateDate != null){
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        if(request.checkNotModified(eTag)){
            return null;
        }

        PaymentWayModel paymentWayModel = paymentWayModelAssembler.toModel(paymentWayService.findById(paymentWayId));

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentWayModel);
    }

    @CheckSecurity.PaymentWays.CanEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentWayModel save(@RequestBody @Valid PaymentWayInput paymentWayInput){
        PaymentWay paymentWay = paymentWayInputDisassembler.toDomainObject(paymentWayInput);

        return paymentWayModelAssembler.toModel(paymentWayService.save(paymentWay));
    }

    @CheckSecurity.PaymentWays.CanEdit
    @Override
    @PutMapping("/{id}")
    public PaymentWayModel update(@PathVariable Long paymentWayId, @RequestBody @Valid PaymentWayInput paymentWayInput){
        PaymentWay paymentWay = paymentWayService.verifyIfExistsOrThrow(paymentWayId);

        paymentWayInputDisassembler.copyToDomainObject(paymentWayInput, paymentWay);

        return paymentWayModelAssembler.toModel(paymentWayService.save(paymentWay));
    }

    @CheckSecurity.PaymentWays.CanEdit
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long paymentWayId){
        paymentWayService.delete(paymentWayId);
    }

}
