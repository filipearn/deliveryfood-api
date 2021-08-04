package arn.filipe.fooddelivery.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal freighRate;

    private Boolean active;

    private LocalDateTime registrationDate;

    private LocalDateTime updateDate;

    @ManyToOne
    private Kitchen kitchen;

    @ManyToOne
    private PaymentWay paymentWay;
}
