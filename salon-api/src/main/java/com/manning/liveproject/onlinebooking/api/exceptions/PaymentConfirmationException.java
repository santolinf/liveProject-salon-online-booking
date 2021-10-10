package com.manning.liveproject.onlinebooking.api.exceptions;

import lombok.Getter;

@Getter
public class PaymentConfirmationException extends RuntimeException {

    private final String property;
    private final String value;

    public PaymentConfirmationException(String message, String property, String value) {
        super(message);
        this.property = property;
        this.value = value;
    }
}
