package arn.filipe.fooddelivery.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

    CREATED("Created"),
    CONFIRMED("Confirmed", CREATED),
    DELIVERED("Delivered", CONFIRMED),
    CANCELLED("Canceled", CREATED);

    private String description;
    private List<OrderStatus> previousStatus;

    OrderStatus(String description, OrderStatus... previousStatus){
        this.description = description;
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public String getDescription(){
        return this.description;
    }

    public boolean cantChangeTo(OrderStatus newStatus){
        return !newStatus.previousStatus.contains(this);
    }

    public boolean canChangeTo(OrderStatus newStatus){
        return !cantChangeTo(newStatus);
    }
}
