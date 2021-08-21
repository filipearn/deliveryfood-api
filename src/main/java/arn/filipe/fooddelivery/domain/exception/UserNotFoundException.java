package arn.filipe.fooddelivery.domain.exception;

public class UserNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        this(String.format("User with id %d not found", id));
    }
}
