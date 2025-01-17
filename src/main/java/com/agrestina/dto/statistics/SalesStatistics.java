package com.agrestina.dto.statistics;

import com.agrestina.domain.product.Category;

import java.math.BigDecimal;

public record SalesStatistics(Category category, Long quantitySale, BigDecimal revenue) {
}
