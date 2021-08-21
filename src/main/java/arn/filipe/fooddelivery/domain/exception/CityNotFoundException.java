package arn.filipe.fooddelivery.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        this(String.format("City with id %d not found", id));
    }
}
