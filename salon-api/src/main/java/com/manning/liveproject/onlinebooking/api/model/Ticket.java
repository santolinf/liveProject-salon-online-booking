package com.manning.liveproject.onlinebooking.api.model;

import com.manning.liveproject.onlinebooking.api.model.enums.TicketStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Ticket extends BaseEntity {

    @OneToOne
    private Payment payment;

    private TicketStatus ticketStatus;
}
