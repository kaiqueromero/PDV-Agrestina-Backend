package com.agrestina.dto.product;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterProductDTO(@NotBlank String name,
                                 @NotBlank String description,
                                 @NotNull BigDecimal price,
                                 @NotNull @Min(1) Integer initialStock,
                                 @NotBlank String category ) {
}