package com.manning.liveproject.onlinebooking.api.controller;

import com.manning.liveproject.onlinebooking.api.controller.dtos.ConfirmPaymentDto;
import com.manning.liveproject.onlinebooking.api.controller.dtos.PaymentDto;
import com.manning.liveproject.onlinebooking.api.controller.dtos.PaymentRequest;
import com.manning.liveproject.onlinebooking.api.controller.mapper.MapStructMapper;
import com.manning.liveproject.onlinebooking.api.model.SalonDetails;
import com.manning.liveproject.onlinebooking.api.model.Ticket;
import com.manning.liveproject.onlinebooking.api.service.PaymentWorkflow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Payment Services")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final SalonDetails salonDetails;
    private final PaymentWorkflow paymentWorkflow;
    private final MapStructMapper mapper;

    @ApiOperation("InitiatePaymentAPI")
    @PostMapping("initiate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PaymentDto initiatePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        return mapper.paymentToPaymentDto(paymentWorkflow.initiatePayment(paymentRequest));
    }

    @ApiOperation("VerifyPaymentAndConfirmSlotAPI")
    @PutMapping("confirm/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ConfirmPaymentDto confirmPayment(@PathVariable("id") String paymentId) {
        Ticket ticket = paymentWorkflow.confirmPayment(paymentId);
        return ConfirmPaymentDto.builder()
                .salonDetails(salonDetails)
                .ticket(mapper.ticketToTicketDto(ticket))
                .build();
    }
}
