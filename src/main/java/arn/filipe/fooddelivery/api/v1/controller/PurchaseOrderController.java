package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.openapi.controller.PurchaseOrderControllerOpenApi;
import arn.filipe.fooddelivery.api.v1.assembler.ItemOrderInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.PurchaseOrderInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.PurchaseOrderModelAssembler;
import arn.filipe.fooddelivery.api.v1.assembler.PurchaseOrderSummaryModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.PurchaseOrderModel;
import arn.filipe.fooddelivery.api.v1.model.input.PurchaseOrderInput;
import arn.filipe.fooddelivery.core.data.PageWrapper;
import arn.filipe.fooddelivery.core.data.PageableTranslator;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.*;
import arn.filipe.fooddelivery.domain.filter.PurchaseOrderFilter;
import arn.filipe.fooddelivery.domain.service.ItemOrderService;
import arn.filipe.fooddelivery.domain.service.PaymentWayService;
import arn.filipe.fooddelivery.domain.service.PurchaseOrderService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/purchase-orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderController implements PurchaseOrderControllerOpenApi {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderModelAssembler purchaseOrderModelAssembler;

    @Autowired
    private PurchaseOrderSummaryModelAssembler purchaseOrderSummaryModelAssembler;

    @Autowired
    private PurchaseOrderInputDisassembler purchaseOrderInputDisassembler;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private PaymentWayService paymentWayService;

    @Autowired
    private ItemOrderInputDisassembler itemOrderInputDisassembler;

    @Autowired
    private ItemOrderService itemOrderService;

    @Autowired
    private PagedResourcesAssembler<PurchaseOrder> purchaseOrderPagedResourcesAssembler;

    @Autowired
    private Security security;

    @CheckSecurity.PurchaseOrders.CanSearch
    @Override
    @GetMapping
    public PagedModel<PurchaseOrderModel> find(PurchaseOrderFilter filter, Pageable pageable){

        Pageable translatedPageable = translatePageable(pageable);

        Page<PurchaseOrder> purchaseOrdersPage = purchaseOrderService.findAll(filter, translatedPageable);

        purchaseOrdersPage = new PageWrapper<>(purchaseOrdersPage, pageable);

        return purchaseOrderPagedResourcesAssembler
                .toModel(purchaseOrdersPage, purchaseOrderModelAssembler);
    }

    @CheckSecurity.PurchaseOrders.CanFind
    @Override
    @GetMapping("/{code}")
    public PurchaseOrderModel findByCode(@PathVariable String code){
        return purchaseOrderModelAssembler.toModel(purchaseOrderService.findByCode(code));
    }

    @CheckSecurity.PurchaseOrders.CanCreate
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderModel save(@RequestBody @Valid PurchaseOrderInput purchaseOrderInput){
        try{
            PurchaseOrder purchaseOrder = purchaseOrderInputDisassembler.toDomainObject(purchaseOrderInput);

            purchaseOrder.setClient(new User());
            purchaseOrder.getClient().setId(security.getUserId());

            purchaseOrder = purchaseOrderService.registerOrder(purchaseOrder);
            return purchaseOrderModelAssembler.toModel(purchaseOrder);
        } catch (EntityNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }

    }

    private Pageable translatePageable(Pageable apiPageable){
        var mapping = ImmutableMap.of(
                "code", "code",
                "clientName", "client.name",
                "restaurant.name", "restaurant.name",
                "totalValue", "totalValue"
        );
        return PageableTranslator.translate(apiPageable, mapping);
    }
}
