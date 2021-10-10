package com.manning.liveproject.onlinebooking.api.service;

import com.manning.liveproject.onlinebooking.api.exceptions.PaymentIntentNotFoundException;
import com.manning.liveproject.onlinebooking.api.model.Payment;
import com.manning.liveproject.onlinebooking.api.model.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment findPaymentByIntentId(String paymentIntentId) {
        return paymentRepository.findByIntentId(paymentIntentId)
                .orElseThrow(() -> new PaymentIntentNotFoundException("Payment cannot be found for intent ID " + paymentIntentId));
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
