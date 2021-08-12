package arn.filipe.fooddelivery.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{

    public KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException(Long id) {
        this(String.format("Kitchen with id %d not found", id));
    }
}
