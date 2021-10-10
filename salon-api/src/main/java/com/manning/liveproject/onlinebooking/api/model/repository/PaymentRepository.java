package com.manning.liveproject.onlinebooking.api.model.repository;

import com.manning.liveproject.onlinebooking.api.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIntentId(String paymentIntentId);
}
