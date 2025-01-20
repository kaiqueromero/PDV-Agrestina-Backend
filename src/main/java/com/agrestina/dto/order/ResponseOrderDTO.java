package com.agrestina.dto.order;

import com.agrestina.domain.order.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseOrderDTO(Long id, LocalDate date, String clientName, String userName, BigDecimal totalValue, String status) {
    public ResponseOrderDTO(Order order) {
        this(order.getId(), order.getDate(), order.getClient().getName(), order.getUser().getName(), order.getTotalValue(), order.getStatus().name());
    }

}
