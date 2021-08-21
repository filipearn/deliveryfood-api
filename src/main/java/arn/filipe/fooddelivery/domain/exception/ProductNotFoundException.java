package arn.filipe.fooddelivery.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        this(String.format("Product with id %d not found", id));
    }
}
