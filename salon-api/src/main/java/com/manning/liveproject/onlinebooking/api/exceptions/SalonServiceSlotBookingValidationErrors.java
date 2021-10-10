package com.manning.liveproject.onlinebooking.api.exceptions;

import com.google.common.collect.Maps;

import java.util.Map;

public class SalonServiceSlotBookingValidationErrors extends RuntimeException {

    private final Map<String, String> propertyDefaultMessages;

    public SalonServiceSlotBookingValidationErrors() {
        super("Invalid field values");
        this.propertyDefaultMessages = Maps.newHashMap();
    }

    public void add(String property, String defaultMessage) {
        propertyDefaultMessages.put(property, defaultMessage);
    }

    public Map<String, String> getDefaultMessages() {
        return Maps.newHashMap(propertyDefaultMessages);
    }

    public void assertNoErrors() {
        if (!propertyDefaultMessages.isEmpty()) {
            throw this;
        }
    }
}
