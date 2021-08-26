package com.manning.liveproject.onlinebooking.api.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    protected Long id;
}
