package com.agrestina.dto.order;

import com.agrestina.domain.order.Order;
import com.agrestina.dto.payment.ResponsePaymentDTO;

import java.time.LocalDate;

public record ResponseOrderDTO(Long id, LocalDate date, String clientName, String userName, double totalValue, String status) {
    public ResponseOrderDTO(Order order) {
        this(order.getId(), order.getDate(), order.getClient().getName(), order.getUser().getName(), order.getTotalValue(), order.getStatus().name());
    }

}
