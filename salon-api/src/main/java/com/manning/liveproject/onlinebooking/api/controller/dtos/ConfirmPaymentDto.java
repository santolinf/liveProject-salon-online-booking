package com.manning.liveproject.onlinebooking.api.controller.dtos;

import com.manning.liveproject.onlinebooking.api.model.SalonDetails;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmPaymentDto {

    private SalonDetails salonDetails;
    private TicketDto ticket;
}
