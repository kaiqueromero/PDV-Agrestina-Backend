package com.agrestina.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterOrderDTO(@NotNull List<OrderedItemDTO> items, String clientId) {
}
