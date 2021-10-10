package com.manning.liveproject.onlinebooking.api.controller.dtos;

import com.manning.liveproject.onlinebooking.api.model.enums.TicketStatus;
import lombok.Data;

@Data
public class TicketDto {

    private Long id;
    private PaymentDto payment;
    private TicketStatus ticketStatus;
}
