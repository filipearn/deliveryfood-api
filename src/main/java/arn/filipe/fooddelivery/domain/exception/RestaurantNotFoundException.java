package arn.filipe.fooddelivery.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long id) {
        this(String.format("Restaurant with id %d not found", id));
    }
}
