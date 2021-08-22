package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.*;
import arn.filipe.fooddelivery.api.model.*;
import arn.filipe.fooddelivery.api.model.input.*;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.*;
import arn.filipe.fooddelivery.domain.repository.filter.PurchaseOrderFilter;
import arn.filipe.fooddelivery.domain.service.ItemOrderService;
import arn.filipe.fooddelivery.domain.service.PaymentWayService;
import arn.filipe.fooddelivery.domain.service.PurchaseOrderService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import arn.filipe.fooddelivery.infrastructure.repository.spec.PurchaseOrderSpecFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

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

    @GetMapping
    public Page<PurchaseOrderModel> find(PurchaseOrderFilter filter, Pageable pageable){
        Page<PurchaseOrder> purchaseOrders = purchaseOrderService.findAll(filter, pageable);

        List<PurchaseOrderModel> purchaseOrdersModel = purchaseOrderModelAssembler.toCollectionModel(purchaseOrders.getContent());

        Page<PurchaseOrderModel> purchaseOrdersModelPage = new PageImpl<>(purchaseOrdersModel, pageable, purchaseOrders.getTotalElements());

        return purchaseOrdersModelPage;
    }

    @GetMapping("/{code}")
    public PurchaseOrderModel findByCode(@PathVariable String code){
        return purchaseOrderModelAssembler.toModel(purchaseOrderService.findByCode(code));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderModel save(@RequestBody @Valid PurchaseOrderInput purchaseOrderInput){
        try{
            PurchaseOrder purchaseOrder = purchaseOrderInputDisassembler.toDomainObject(purchaseOrderInput);

            //TODO get authenticated user
            purchaseOrder.setClient(new User());
            purchaseOrder.getClient().setId(1L);

            purchaseOrder = purchaseOrderService.registerOrder(purchaseOrder);
            return purchaseOrderModelAssembler.toModel(purchaseOrder);
        } catch (EntityNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }

    }
}
