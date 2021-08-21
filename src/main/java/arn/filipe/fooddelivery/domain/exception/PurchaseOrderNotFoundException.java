package arn.filipe.fooddelivery.domain.exception;

public class PurchaseOrderNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public PurchaseOrderNotFoundException(String message) {
        super(message);
    }

    public PurchaseOrderNotFoundException(Long id) {
        this(String.format("Purchase Order with id %d not found", id));
    }
}
