package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.core.validation.FreighRate;
import arn.filipe.fooddelivery.core.validation.Groups;
import arn.filipe.fooddelivery.core.validation.ZeroValueIncludeDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ZeroValueIncludeDescription(valueField = "FreighRate", descriptionField = "name", requiredDescription = "Free shipping")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @PositiveOrZero(message = "{FreighRate.invalid}")
    @FreighRate
    @NotNull
    @Column(nullable = false)
    private BigDecimal freighRate;

    private Boolean active;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime registrationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime updateDate;

    @Valid
    @ConvertGroup(from = Default.class, to= Groups.KitchenId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(name = "restaurant_payment_way",
    joinColumns = @JoinColumn(name = "restaurant_id"),
    inverseJoinColumns = @JoinColumn(name = "payment_way_id"))
    private List<PaymentWay> paymentWay = new ArrayList<>();
}
