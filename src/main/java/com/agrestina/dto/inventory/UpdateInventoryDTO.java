package com.agrestina.dto.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateInventoryDTO(@NotNull Long productId, @NotNull @Min(1) Integer quantity) {
}
