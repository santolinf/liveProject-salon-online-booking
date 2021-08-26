package com.manning.liveproject.onlinebooking.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Slot extends BaseEntity {

    private LocalDateTime confirmedAt;
    private LocalDateTime lockedAt;
    private LocalDateTime slotFor;
    private Integer status;
    private String stylistName;
    @ManyToOne(fetch = FetchType.LAZY)
    private SalonServiceDetail selectedService;
}
