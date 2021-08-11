package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private BigDecimal freighRate;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    private LocalDateTime confirmationDate;

    private LocalDateTime cancellationDate;

    private LocalDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @JsonIgnore
    @OneToMany(mappedBy = "purchase_order")
    private List<ItemOrder> item = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "payment_way_id", nullable = false)
    private PaymentWay paymentWay;

    @Column(nullable = false)
    private OrderStatus status;

}
