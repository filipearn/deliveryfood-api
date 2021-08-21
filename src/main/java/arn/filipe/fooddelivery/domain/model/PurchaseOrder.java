package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.api.model.input.PurchaseOrderInput;
import arn.filipe.fooddelivery.domain.enums.OrderStatus;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class PurchaseOrder {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    }

    public void cancel(){
        setStatus(OrderStatus.CANCELLED);
        setCancellationDate(OffsetDateTime.now());
    }

    public void delivery(){
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now());
    }

    private void setStatus(OrderStatus orderStatus){
        if(getStatus().cantChangeTo(orderStatus)){
            throw new BusinessException(
                    String.format("Purchase order status %d can't be changed from %s to %s",
                            getId(), getStatus().getDescription(), orderStatus.getDescription()));
        }

        this.status = orderStatus;
    }


}
