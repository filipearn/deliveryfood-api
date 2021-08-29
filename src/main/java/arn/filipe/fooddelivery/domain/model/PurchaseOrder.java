package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.domain.enums.OrderStatus;
import arn.filipe.fooddelivery.domain.event.PurchaseOrderCancelledEvent;
import arn.filipe.fooddelivery.domain.event.PurchaseOrderConfirmedEvent;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Data
public class PurchaseOrder extends AbstractAggregateRoot<PurchaseOrder> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(nullable = false)
    private BigDecimal subTotal;

    @Column(nullable = false)
    private BigDecimal freightRate;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime registrationDate;

    private OffsetDateTime confirmationDate;

    private OffsetDateTime cancellationDate;

    private OffsetDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @JsonIgnore
    @OneToMany(mappedBy = "purchase_order", cascade = CascadeType.ALL)
    private List<ItemOrder> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_way_id", nullable = false)
    private PaymentWay paymentWay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    public boolean canBeConfirmed(){
        return getStatus().canChangeTo(OrderStatus.CONFIRMED);
    }

    public boolean canBeCancelled(){
        return getStatus().canChangeTo(OrderStatus.CANCELLED);
    }

    public boolean canBeDelivered(){
        return getStatus().canChangeTo(OrderStatus.DELIVERED);
    }

    public void calculateTotalValue() {
        getItems().forEach(ItemOrder::calculateTotalPrice);

        this.subTotal = getItems().stream()
                .map(item -> item.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalValue = this.subTotal.add(this.freightRate);
    }

    public void confirm(){
        setStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now());

        registerEvent(new PurchaseOrderConfirmedEvent(this));

    }

    public void cancel(){
        setStatus(OrderStatus.CANCELLED);
        setCancellationDate(OffsetDateTime.now());

        registerEvent(new PurchaseOrderCancelledEvent(this));
    }

    public void delivery(){
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now());
    }

    private void setStatus(OrderStatus orderStatus){
        if(getStatus().cantChangeTo(orderStatus)){
            throw new BusinessException(
                    String.format("Purchase order status %s can't be changed from %s to %s",
                            getCode(), getStatus().getDescription(), orderStatus.getDescription()));
        }

        this.status = orderStatus;
    }

    @PrePersist
    private void generateCode(){
        setCode(UUID.randomUUID().toString());
    }


}
