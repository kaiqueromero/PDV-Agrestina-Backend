package com.agrestina.dto.order;

import com.agrestina.domain.client.Client;
import com.agrestina.domain.order.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record OrderDTO(Long id, LocalDate date, List<OrderedItemDTO> items, double totalValue) {
    public OrderDTO(Order order) {
        this(order.getId(), order.getDate(), order.getItems().stream().map(OrderedItemDTO::new).collect(Collectors.toList()), order.getTotalValue());
    }
}
