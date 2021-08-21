package arn.filipe.fooddelivery.domain.exception;

public class TeamNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;
    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(Long id) {
        this(String.format("Team with id %d not found", id));
    }
}
