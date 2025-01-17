package com.agrestina.dto.statistics;

import java.math.BigDecimal;
import java.util.List;

public record ProductReport(BigDecimal totalRevenue, List<ProductsStatistics> statistics) {
}
