package com.manning.liveproject.onlinebooking.api.service;

import com.manning.liveproject.onlinebooking.api.exceptions.TicketNotFoundException;
import com.manning.liveproject.onlinebooking.api.model.Payment;
import com.manning.liveproject.onlinebooking.api.model.Ticket;
import com.manning.liveproject.onlinebooking.api.model.enums.TicketStatus;
import com.manning.liveproject.onlinebooking.api.model.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket createTicket(Payment payment) {
        return ticketRepository.save(
                Ticket.builder().payment(payment).ticketStatus(TicketStatus.BOOKED).build()
        );
    }

    public Ticket verifyTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        // TODO what does a valid ticket look like? status === BOOKED?
        return ticket;
    }
}
