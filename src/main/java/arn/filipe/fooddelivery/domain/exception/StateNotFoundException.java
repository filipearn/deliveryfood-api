package arn.filipe.fooddelivery.domain.exception;

public class StateNotFoundException extends EntityNotFoundException{
    public StateNotFoundException(String message) {
        super(message);
    }
    public StateNotFoundException(Long id) {
        this(String.format("State with id %d not found", id));
    }
}
