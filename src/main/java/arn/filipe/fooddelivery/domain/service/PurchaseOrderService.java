package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.PurchaseOrderNotFoundException;
import arn.filipe.fooddelivery.domain.model.*;
import arn.filipe.fooddelivery.domain.repository.PurchaseOrderRepository;
import arn.filipe.fooddelivery.domain.repository.filter.PurchaseOrderFilter;
import arn.filipe.fooddelivery.infrastructure.repository.spec.PurchaseOrderSpecFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemOrderService itemOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentWayService paymentWayService;

    public List<PurchaseOrder> listAll(){
        return purchaseOrderRepository.findAll();
    }

//    public PurchaseOrder findById(Long id){
//        return verifyIfExistsOrThrow(id);
//    }

    public PurchaseOrder findByCode(String code){
        return verifyIfExistsOrThrow(code);
    }

    @Transactional
    public PurchaseOrder registerOrder(PurchaseOrder purchaseOrder){
        purchaseOrderValidation(purchaseOrder);
        itemsValidation(purchaseOrder);

        purchaseOrder.setFreightRate(purchaseOrder.getRestaurant().getFreightRate());
        purchaseOrder.calculateTotalValue();

        return purchaseOrderRepository.save(purchaseOrder);

    }

    public PurchaseOrder verifyIfExistsOrThrow(String code) {
        return purchaseOrderRepository.findByCode(code)
                .orElseThrow(() -> new PurchaseOrderNotFoundException(code));
    }

//    public PurchaseOrder verifyIfExistsOrThrow(Long id) {
//        return purchaseOrderRepository.findById(id)
//                .orElseThrow(() -> new PurchaseOrderNotFoundException(id));
//    }

    private void itemsValidation(PurchaseOrder purchaseOrder){
        purchaseOrder.getItems().forEach(
                itemOrder -> {
                    Product product = productService.verifyIfExistsOrThrow(
                            purchaseOrder.getRestaurant().getId(), itemOrder.getProduct().getId());

                    itemOrder.setPurchase_order(purchaseOrder);
                    itemOrder.setProduct(product);
                    itemOrder.setUnitPrice(product.getPrice());
                });
    }

    private void purchaseOrderValidation(PurchaseOrder purchaseOrder) {
        City city = cityService.verifyIfExistsOrThrow(purchaseOrder.getAddress().getCity().getId());
        User client = userService.verifyIfExistsOrThrow(purchaseOrder.getClient().getId());
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(purchaseOrder.getRestaurant().getId());
        PaymentWay paymentWay = paymentWayService.verifyIfExistsOrThrow(purchaseOrder.getPaymentWay().getId());

        if(!restaurant.containsPaymentWay(paymentWay)){
            throw new BusinessException(String.format("Payment way '%s' is not accepted in this restaurant.",
                    paymentWay.getDescription()));
        }

        purchaseOrder.getAddress().setCity(city);
        purchaseOrder.setClient(client);
        purchaseOrder.setRestaurant(restaurant);
        purchaseOrder.setPaymentWay(purchaseOrder.getPaymentWay());
    }


    public List<PurchaseOrder> findAll(PurchaseOrderFilter filter) {
        return purchaseOrderRepository.findAll(PurchaseOrderSpecFactory.usingFilter(filter));
    }
}
