package com.manning.liveproject.onlinebooking.api.model;

import lombok.Data;

@Data
public class SalonDetails {

    private String name;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String phone;
}
