package com.agrestina.dto.statistics;

import java.math.BigDecimal;
import java.util.List;

public record BillingReport(BigDecimal totalRevenue, List<SalesStatistics> statistics) {
}