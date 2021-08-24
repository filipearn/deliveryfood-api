package arn.filipe.fooddelivery.infrastructure.service.email;

import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;

public class EmailException extends RuntimeException {

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
