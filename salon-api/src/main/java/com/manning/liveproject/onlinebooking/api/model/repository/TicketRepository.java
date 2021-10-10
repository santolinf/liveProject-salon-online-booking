package com.manning.liveproject.onlinebooking.api.model.repository;

import com.manning.liveproject.onlinebooking.api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
