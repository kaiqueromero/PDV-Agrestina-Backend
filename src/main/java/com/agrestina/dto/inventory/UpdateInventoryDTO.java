package com.agrestina.dto.inventory;

import com.agrestina.exeption.ValidationUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateInventoryDTO(@NotNull Long productId, @NotNull @Min(1) Integer quantity) {
    public UpdateInventoryDTO {
        ValidationUtil.validateNotNull(productId, "productId");
        ValidationUtil.validateMin(quantity, "quantity");
    }
}
