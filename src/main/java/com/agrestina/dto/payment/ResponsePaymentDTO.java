package com.agrestina.dto.payment;


import com.agrestina.domain.payment.Payment;
import com.agrestina.domain.payment.PaymentMethod;

import java.time.LocalDate;

public record ResponsePaymentDTO(String id, Long orderId, PaymentMethod method, LocalDate paymentDate, String receipt) {
    public ResponsePaymentDTO(Payment payment) {
        this(payment.getId(), payment.getOrder().getId(), payment.getMethod(), payment.getPaymentDate(), payment.getReceipt());
    }
}