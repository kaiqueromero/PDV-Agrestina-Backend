package com.agrestina.dto.payment;

import com.agrestina.domain.payment.Payment;
import com.agrestina.domain.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public record PaymentDTO(PaymentMethod method, LocalDate paymentDate, String receipt) {
    public PaymentDTO(Payment payment) {
        this(payment.getMethod(), payment.getPaymentDate(), payment.getReceipt());
    }
}