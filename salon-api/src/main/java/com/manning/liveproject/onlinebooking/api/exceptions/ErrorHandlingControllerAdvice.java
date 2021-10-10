package com.manning.liveproject.onlinebooking.api.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Map;

enum ErrorCode {

    BAD_REQUEST,                // 400
    UNAVAILABLE,                // 404
    PAYMENT_UNSUCCESSFUL,       // 422
    INTERNAL_SERVER_ERROR       // 500
}

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class ErrorResponse {

    private LocalDateTime timestamp;
    private ErrorCode code;
    private String message;
    @Singular
    private Map<String, String> violations;
}

@ControllerAdvice
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintValidationException(ConstraintViolationException e) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.BAD_REQUEST);

        e.getConstraintViolations().forEach(violation ->
            errorResponse.violation(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return new ResponseEntity<>(errorResponse.build(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SalonServiceSlotBookingValidationErrors.class)
    public ResponseEntity<ErrorResponse> handleSalonServiceSlotBookingValidationErrors(SalonServiceSlotBookingValidationErrors e) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.UNAVAILABLE)
                .message(e.getMessage());

        e.getDefaultMessages().forEach(errorResponse::violation);

        return new ResponseEntity<>(errorResponse.build(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentIntentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentIntentNotFoundException(PaymentIntentNotFoundException e) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.UNAVAILABLE)
                .message(e.getMessage());

        return new ResponseEntity<>(errorResponse.build(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFoundException(TicketNotFoundException e) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.UNAVAILABLE)
                .message(e.getMessage());

        errorResponse.violation("ticketId", e.getId().toString());

        return new ResponseEntity<>(errorResponse.build(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentConfirmationException.class)
    public ResponseEntity<ErrorResponse> handlePaymentConfirmationException(PaymentConfirmationException e) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.PAYMENT_UNSUCCESSFUL)
                .message(e.getMessage());

        errorResponse.violation(e.getProperty(), e.getValue());

        return new ResponseEntity<>(errorResponse.build(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<ErrorResponse> handlePaymentProcessingException(PaymentProcessingException e) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.PAYMENT_UNSUCCESSFUL)
                .message(e.getMessage());

        return new ResponseEntity<>(errorResponse.build(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.BAD_REQUEST)
                .message("Field values violations");

        e.getFieldErrors().forEach(fieldError -> {
            errorResponse.violation(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(errorResponse.build(), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception e,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErrorResponse.ErrorResponseBuilder errorResponse = createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());

        return new ResponseEntity<>(errorResponse.build(), headers, status);
    }

    private ErrorResponse.ErrorResponseBuilder createErrorResponse(ErrorCode code) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(code);
    }
}
