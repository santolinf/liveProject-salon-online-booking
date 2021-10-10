package com.manning.liveproject.onlinebooking.api.exceptions;

public class PaymentIntentNotFoundException extends RuntimeException {

    public PaymentIntentNotFoundException(String message) {
        super(message);
    }
}
