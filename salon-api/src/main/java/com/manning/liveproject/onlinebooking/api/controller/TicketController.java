package com.manning.liveproject.onlinebooking.api.controller;

import com.manning.liveproject.onlinebooking.api.controller.dtos.TicketDto;
import com.manning.liveproject.onlinebooking.api.controller.mapper.MapStructMapper;
import com.manning.liveproject.onlinebooking.api.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Ticket Services")
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final MapStructMapper mapper;

    @ApiOperation("VerifyTicketAPI")
    @GetMapping("{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TicketDto verifyTicket(@PathVariable Long ticketId) {
        return mapper.ticketToTicketDto(ticketService.verifyTicket(ticketId));
    }
}
