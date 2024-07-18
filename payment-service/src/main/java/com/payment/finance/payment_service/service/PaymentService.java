package com.payment.finance.payment_service.service;

import com.payment.finance.payment_service.entity.*;
import com.payment.finance.payment_service.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Double amount) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setStatus("CREATED");
        payment.setCreatedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment processPayment(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setStatus("PROCESSED");
            payment.setProcessedAt(LocalDateTime.now());
            return paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Payment not found");
        }
    }

    public Payment getPaymentStatus(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Transactional
    public Payment refundPayment(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setStatus("REFUNDED");
            payment.setRefundedAt(LocalDateTime.now());
            return paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Payment not found");
        }
    }
}