package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.core.validation.FreightRate;
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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ZeroValueIncludeDescription(valueField = "FreightRate", descriptionField = "name", requiredDescription = "Free shipping")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal freightRate;

    private Boolean active = Boolean.TRUE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registrationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updateDate;

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

    public void activate(){
        setActive(true);
    }

    public void deactivate(){
        setActive(false);
    }
}
