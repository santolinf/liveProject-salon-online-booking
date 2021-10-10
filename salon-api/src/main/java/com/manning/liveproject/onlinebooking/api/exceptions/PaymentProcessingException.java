package com.manning.liveproject.onlinebooking.api.exceptions;

public class PaymentProcessingException extends RuntimeException {

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
