package com.payment.finance.payment_service.repository;

import com.payment.finance.payment_service.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}