package com.agrestina.dto.order;

import com.agrestina.domain.order.Order;

import java.time.LocalDate;

public record OrderResponseDTO(Long id, LocalDate date, String clientName, String userName, double totalValue, String status) {
    public OrderResponseDTO(Order order) {
        this(order.getId(), order.getDate(), order.getClient().getName(), order.getUser().getName(), order.getTotalValue(), order.getStatus().name());
    }
}
