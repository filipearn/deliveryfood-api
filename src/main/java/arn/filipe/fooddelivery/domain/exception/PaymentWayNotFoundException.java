package arn.filipe.fooddelivery.domain.exception;

public class PaymentWayNotFoundException extends EntityNotFoundException{

    private static final long serialVersionUID = 1L;

    public PaymentWayNotFoundException(String message) {
        super(message);
    }

    public PaymentWayNotFoundException(Long id) {
        this(String.format("Payment way with id %d not found", id));
    }
}
