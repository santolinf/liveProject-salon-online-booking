package com.manning.liveproject.onlinebooking.api.controller.dtos;

import com.manning.liveproject.onlinebooking.api.model.enums.SlotStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotDto {

    private Long id;
    private LocalDateTime confirmedAt;
    private LocalDateTime lockedAt;
    private LocalDateTime slotFor;
    private SlotStatus status;
    private String stylistName;

    private SalonServiceDto selectedService;
}
