package com.agrestina.repository;

import com.agrestina.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, String> {
}
