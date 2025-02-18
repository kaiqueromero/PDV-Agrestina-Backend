package com.agrestina.dto.order;

import com.agrestina.exeption.ValidationUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterOrderDTO(@NotNull List<OrderedItemDTO> items, String clientId) {
    public RegisterOrderDTO {
        ValidationUtil.validateList(items, "items");
        ValidationUtil.validateNotNullOrBlank(clientId, "clientId");
    }
}
