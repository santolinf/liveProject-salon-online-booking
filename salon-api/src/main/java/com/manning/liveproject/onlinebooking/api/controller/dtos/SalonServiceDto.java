package com.manning.liveproject.onlinebooking.api.controller.dtos;

import lombok.Data;

@Data
public class SalonServiceDto {

    private Long id;
    private String name;
    private String description;
    private Long price;
    private Integer timeInMinutes;
}
