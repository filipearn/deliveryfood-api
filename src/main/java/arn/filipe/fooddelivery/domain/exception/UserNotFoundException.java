package arn.filipe.fooddelivery.domain.exception;

public class UserNotFoundException extends EntityNotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        this(String.format("User with id %d not found", id));
    }
}
