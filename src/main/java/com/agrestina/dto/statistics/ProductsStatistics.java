package com.agrestina.dto.statistics;

import java.math.BigDecimal;

public record ProductsStatistics(String name, Long quantity, BigDecimal revenue) {
}
