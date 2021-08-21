package arn.filipe.fooddelivery.domain.exception;

public class PurchaseOrderNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public PurchaseOrderNotFoundException(String code) {
        super(String.format("Purchase Order with id %s not found", code));
    }
}
