package arn.filipe.fooddelivery.domain.exception;

public class ItemOrderNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public ItemOrderNotFoundException(String message) {
        super(message);
    }

    public ItemOrderNotFoundException(Long id) {
        this(String.format("Item order with id %d not found", id));
    }
}
