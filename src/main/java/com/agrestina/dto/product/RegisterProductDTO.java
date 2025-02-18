package com.agrestina.dto.product;


import com.agrestina.exeption.ValidationUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterProductDTO(@NotBlank String name, @NotBlank String description, @NotNull BigDecimal price, @NotNull @Min(1) Integer initialStock, @NotBlank String category ) {
    public RegisterProductDTO {
        ValidationUtil.validateNotNullOrBlank(name, "name");
        ValidationUtil.validateNotNullOrBlank(description, "description");
        ValidationUtil.validatePositive(price, "price");
        ValidationUtil.validateMin(initialStock, "initialStock");
        ValidationUtil.validateNotNullOrBlank(category, "category");
    }
}