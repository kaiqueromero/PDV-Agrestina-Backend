package com.agrestina.dto.product;

import com.agrestina.domain.product.Product;

import java.math.BigDecimal;

public record ProductDTO(Long id, String name, String description, BigDecimal price, Boolean active) {
    public ProductDTO(Product product){
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.isActive());
    }
}