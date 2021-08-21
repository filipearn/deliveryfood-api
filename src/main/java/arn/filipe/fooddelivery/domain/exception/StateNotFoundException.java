package arn.filipe.fooddelivery.domain.exception;

public class StateNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String message) {
        super(message);
    }
    public StateNotFoundException(Long id) {
        this(String.format("State with id %d not found", id));
    }
}
