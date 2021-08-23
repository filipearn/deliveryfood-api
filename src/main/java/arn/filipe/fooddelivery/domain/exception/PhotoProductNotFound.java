package arn.filipe.fooddelivery.domain.exception;

public class PhotoProductNotFound extends EntityNotFoundException{

    private static final long serialVersionUID = 1L;

    public PhotoProductNotFound(String message) {
        super(message);
    }

    public PhotoProductNotFound(Long restaurantId, Long productId) {
        this(String.format("Photo not found for Product '%s' of the restaurant '%s'", restaurantId, productId));
    }
}
