package arn.filipe.fooddelivery.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable reason) {
        super(message, reason);
    }

}