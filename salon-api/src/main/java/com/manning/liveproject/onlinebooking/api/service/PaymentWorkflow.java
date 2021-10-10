package com.manning.liveproject.onlinebooking.api.service;

import com.google.common.collect.Maps;
import com.manning.liveproject.onlinebooking.api.controller.dtos.PaymentRequest;
import com.manning.liveproject.onlinebooking.api.exceptions.SalonServiceSlotBookingValidationErrors;
import com.manning.liveproject.onlinebooking.api.exceptions.PaymentConfirmationException;
import com.manning.liveproject.onlinebooking.api.model.Payment;
import com.manning.liveproject.onlinebooking.api.model.SalonServiceDetail;
import com.manning.liveproject.onlinebooking.api.model.Slot;
import com.manning.liveproject.onlinebooking.api.model.Ticket;
import com.manning.liveproject.onlinebooking.api.model.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * https://stripe.com/docs/payments/payment-intents
 * https://stripe.com/docs/api
 * https://www.youtube.com/playlist?list=PLy1nL-pvL2M5TnSGVjEHTTMgdnnHi-KPg
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentWorkflow {

    private final SalonService salonService;
    private final PaymentService paymentService;
    private final TicketService ticketService;
    private final PaymentProcessingGateway paymentProcessingGateway;

    public Payment initiatePayment(PaymentRequest paymentRequest) {
        // use map entry to implement a tuple
        Map.Entry<SalonServiceDetail, Slot> selectedServiceSlot = findSelectedServiceAndSlot(paymentRequest);

        SalonServiceDetail selectedService = selectedServiceSlot.getKey();
        Slot slot = selectedServiceSlot.getValue();

        Payment payment = generatePaymentIntent(paymentRequest, selectedService, slot);

        lockSlot(slot, selectedService);

        return payment;
    }

    private Map.Entry<SalonServiceDetail, Slot> findSelectedServiceAndSlot(PaymentRequest paymentRequest) {
        SalonServiceSlotBookingValidationErrors validationErrors = new SalonServiceSlotBookingValidationErrors();

        SalonServiceDetail selectedService = salonService.getSalonServiceDetail(paymentRequest.getSalonServiceDetailId())
                .or(() -> {
                    validationErrors.add(
                            "salonServiceDetailsId",
                            "Salon service details not found for ID: " + paymentRequest.getSalonServiceDetailId()
                    );
                    return Optional.empty();
                }).orElse(null);

        Slot slot = salonService.getAvailableSlot(paymentRequest.getSlotId())
                .or(() -> {
                    validationErrors.add(
                            "slotId",
                            "Slot not found or not available for ID: " + paymentRequest.getSlotId()
                    );
                    return Optional.empty();
                }).orElse(null);

        validationErrors.assertNoErrors();

        return Maps.immutableEntry(selectedService, slot);
    }

    public Ticket confirmPayment(String paymentIntentId) {
        Payment payment = updatePaymentStatus(paymentIntentId);

        if (!PaymentStatus.SUCCESS.equals(payment.getStatus())) {
            throw new PaymentConfirmationException("Payment is not complete or is cancelled", "paymentStatus", payment.getStatus().toString());
        }

        return processSuccessfulPayment(payment);
    }

    private Ticket processSuccessfulPayment(Payment payment) {
        confirmSlot(payment.getSlot());

        return generateTicket(payment);
    }

    private Payment generatePaymentIntent(
            PaymentRequest paymentRequest,
            SalonServiceDetail selectedService,
            Slot slot
    ) {
        Long amount = selectedService.getPrice();
        PaymentIntentDto paymentIntent = paymentProcessingGateway.createNewPaymentIntent(amount);

        return paymentService.savePayment(Payment.builder()
                .amount(amount)
                .status(PaymentStatus.PROCESSING)
                .intentId(paymentIntent.getId())
                .clientSecret(paymentIntent.getClientSecret())
                .firstName(paymentRequest.getFirstName())
                .lastName(paymentRequest.getLastName())
                .email(paymentRequest.getEmail())
                .phoneNumber(paymentRequest.getPhoneNumber())
                .selectedService(selectedService)
                .slot(slot)
                .build()
        );
    }

    private Payment updatePaymentStatus(String paymentIntentId) {
        PaymentIntentDto paymentIntent = paymentProcessingGateway.retrievePaymentIntent(paymentIntentId);
        log.info("payment intent {}", paymentIntent);

        Payment payment = paymentService.findPaymentByIntentId(paymentIntent.getId());

        String status = paymentIntent.getStatus();
        if (paymentProcessingGateway.isPaymentFlowSuccessful(status)) {
            payment.setStatus(PaymentStatus.SUCCESS);
        } else if (paymentProcessingGateway.isPaymentFlowCancelled(status)) {
            payment.setStatus(PaymentStatus.FAILED);
        }

        return paymentService.savePayment(payment);
    }

    private Ticket generateTicket(Payment payment) {
        return ticketService.createTicket(payment);
    }

    private void lockSlot(Slot slot, SalonServiceDetail selectedService) {
        salonService.lockSlot(slot.getId(), selectedService.getId());
    }

    private void confirmSlot(Slot slot) {
        salonService.confirmSlot(slot.getId());
    }
}
