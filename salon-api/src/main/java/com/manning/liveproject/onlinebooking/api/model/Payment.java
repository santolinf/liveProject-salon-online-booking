package com.manning.liveproject.onlinebooking.api.model;

import com.manning.liveproject.onlinebooking.api.model.enums.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Payment extends BaseEntity {

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

    @OneToOne(fetch = FetchType.LAZY)
    private Slot slot;
    @ManyToOne(fetch = FetchType.LAZY)
    private SalonServiceDetail selectedService;

    @PrePersist
    void createdAndUpdatedTimestamp() {
        created = updated = LocalDateTime.now();
    }

    @PreUpdate
    void updatedTimestamp() {
        updated = LocalDateTime.now();
    }
}
