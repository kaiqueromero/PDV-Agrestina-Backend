package com.agrestina.dto.order;

import com.agrestina.domain.orderedItems.OrderedItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderedItemDTO(@NotNull Long productId, @NotNull @Min(1) Integer quantity) {
    public OrderedItemDTO(OrderedItem orderedItem) {
        this(orderedItem.getProduct().getId(), orderedItem.getQuantity());
    }
}
