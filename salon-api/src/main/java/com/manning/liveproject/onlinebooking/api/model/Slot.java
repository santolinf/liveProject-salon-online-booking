package com.manning.liveproject.onlinebooking.api.model;

import com.manning.liveproject.onlinebooking.api.model.enums.SlotStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Slot extends BaseEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    private List<SalonServiceDetail> availableServices;

    private LocalDateTime confirmedAt;
    private LocalDateTime lockedAt;
    private LocalDateTime slotFor;
    private SlotStatus status;
    private String stylistName;
    @ManyToOne(fetch = FetchType.LAZY)
    private SalonServiceDetail selectedService;
}
