package com.agrestina.dto.inventory;

import com.agrestina.domain.inventory.Inventory;

public record InventoryDTO(Long productId, Integer quantity) {
    public InventoryDTO(Inventory inventory){
        this(inventory.getProduct().getId(), inventory.getQuantity());
    }
}
