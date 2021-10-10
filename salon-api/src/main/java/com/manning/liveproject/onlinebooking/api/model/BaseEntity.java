package com.manning.liveproject.onlinebooking.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
