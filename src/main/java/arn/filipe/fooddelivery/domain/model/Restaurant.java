package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.core.validation.ZeroValueIncludeDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Product> products = new HashSet<>();

    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(name = "restaurant_payment_way",
    joinColumns = @JoinColumn(name = "restaurant_id"),
    inverseJoinColumns = @JoinColumn(name = "payment_way_id"))
    private Set<PaymentWay> paymentWays = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurant_user",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @NotNull
    private boolean opened;

    public boolean isOpen() {
        return this.opened;
    }

    public boolean isClose() {
        return !isOpen();
    }

    public boolean isActivated(){
        return this.active;
    }

    public boolean isDeactivated(){
        return !isActivated();
    }

    public void activate(){
        setActive(true);
    }

    public void deactivate(){
        setActive(false);
    }

    public void open(){
        setOpened(true);
    }

    public void close(){
        setOpened(false);
    }

    public boolean allowedOpen() {
        return isActivated() && isClose();
    }

    public boolean allowedActivation() {
        return isDeactivated();
    }

    public boolean allowedDeactivation() {
        return isActivated();
    }

    public boolean allowedClosure() {
        return isOpened();
    }

    public boolean associatePaymentWay(PaymentWay paymentWay){
        return this.getPaymentWays().add(paymentWay);
    }

    public boolean disassociatePaymentWay(PaymentWay paymentWay){
        return this.getPaymentWays().remove(paymentWay);
    }

    public boolean containsProduct(Product product){
        return this.getProducts().contains(product);
    }

    public boolean containsPaymentWay(PaymentWay paymentWay){
        return this.getPaymentWays().contains(paymentWay);
    }

    public boolean associateProduct(Product product){
        return this.getProducts().add(product);
    }

    public boolean disassociateProduct(Product product){
        return this.getProducts().remove(product);
    }

    public boolean associateUser(User user){
        return this.getUsers().add(user);
    }

    public boolean disassociateUser(User user){
        return this.getUsers().remove(user);
    }
}
