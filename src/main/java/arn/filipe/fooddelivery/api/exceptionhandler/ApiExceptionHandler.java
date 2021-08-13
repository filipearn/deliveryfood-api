package arn.filipe.fooddelivery.api.exceptionhandler;

import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MESSAGE = "An unexpected internal system error has occurred. " +
            "Please try again and if the problem persists, contact your system administrator.";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof MethodArgumentTypeMismatchException){
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }


    ApiErrorType apiErrorType = ApiErrorType.INVALID_MESSAGE;
        String detail = "The request body is invalid. Check syntax error";

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiErrorType apiErrorType = ApiErrorType.GENERIC_SYSTEM_ERROR;
        String detail = GENERIC_ERROR_MESSAGE;

        ex.printStackTrace();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorType apiErrorType = ApiErrorType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        ApiErrorType apiErrorType = ApiErrorType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorType apiErrorType = ApiErrorType.BUSINESS_ERROR;
        String detail = ex.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ApiError.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType, String detail){
        return ApiError.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail);
    }
    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        ApiErrorType apiErrorType = ApiErrorType.INVALID_MESSAGE;
        String detail = String.format("The property '%s' received a value '%s', that is a invalid type. " +
                "Inform an %s-compatible value.", path, ex.getValue(), ex.getTargetType().getSimpleName());

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        ApiErrorType apiErrorType = ApiErrorType.INVALID_MESSAGE;
        String detail = String.format("The property '%s' is invalid for this resource. " +
                "Fix or remove this property to continue.", path);

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        status = HttpStatus.NOT_FOUND;
        ApiErrorType apiErrorType = ApiErrorType.RESOURCE_NOT_FOUND;
        String detail = String.format("The resource '%s' that you tried to access is nonexistent.", ex.getRequestURL());

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.INVALID_PARAMETER;
        String detail = String.format("The parameter witch URL '%s' received a value '%s, that is a invalid type." +
                "Fix it and inform an %s-compatible value.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

}
