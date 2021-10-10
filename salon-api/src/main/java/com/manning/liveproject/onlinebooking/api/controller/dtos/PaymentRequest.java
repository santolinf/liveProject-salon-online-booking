package com.manning.liveproject.onlinebooking.api.controller.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PaymentRequest {

    @NotNull
    private Long slotId;
    @NotNull
    private Long salonServiceDetailId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
