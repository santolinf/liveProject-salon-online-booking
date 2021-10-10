package com.manning.liveproject.onlinebooking.api.exceptions;

import lombok.Getter;

@Getter
public class TicketNotFoundException extends RuntimeException {

    private final Long id;

    public TicketNotFoundException(Long id) {
        super("Ticket not found");
        this.id = id;
    }
}
