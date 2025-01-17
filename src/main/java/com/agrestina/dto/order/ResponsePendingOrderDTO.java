package com.agrestina.dto.order;

import com.agrestina.domain.order.Order;
import com.agrestina.domain.order.PendingOrder;
import com.agrestina.repository.PendingOrderRepository;

import java.time.LocalDate;

public record ResponsePendingOrderDTO(Long id, LocalDate date, String clientName, String userName, double totalValue, String status) {
    public ResponsePendingOrderDTO(PendingOrder pendingOrder) {
        this(pendingOrder.getId(), pendingOrder.getDate(), pendingOrder.getClient().getName(), pendingOrder.getUser().getName(), pendingOrder.getTotalValue(), pendingOrder.getStatus().name());
    }
}
