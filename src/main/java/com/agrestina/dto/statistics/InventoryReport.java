package com.agrestina.dto.statistics;

import com.agrestina.dto.product.ProductDTO;

import java.util.List;

public record InventoryReport(List<ProductDTO> missingProducts) {
}
