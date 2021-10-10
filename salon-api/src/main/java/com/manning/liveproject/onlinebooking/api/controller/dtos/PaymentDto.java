package com.manning.liveproject.onlinebooking.api.controller.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manning.liveproject.onlinebooking.api.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDto {

    @JsonIgnore
    private Long id;
    private Long amount;
    private PaymentStatus status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String intentId;
    private String clientSecret;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private SlotDto slot;
    private SalonServiceDto selectedService;
}
