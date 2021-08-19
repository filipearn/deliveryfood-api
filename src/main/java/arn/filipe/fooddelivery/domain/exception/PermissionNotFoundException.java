package arn.filipe.fooddelivery.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException{
    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long id) {
        this(String.format("Permission with id %d not found", id));
    }
}
