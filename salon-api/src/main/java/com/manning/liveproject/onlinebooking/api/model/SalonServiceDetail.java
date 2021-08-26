package com.manning.liveproject.onlinebooking.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class SalonServiceDetail extends BaseEntity {

    private String description;
    private String name;
    private Long price;
    private Integer timeInMinutes;
}
