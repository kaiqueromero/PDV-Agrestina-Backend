package com.agrestina.repository;

import com.agrestina.domain.inventory.Inventory;
import com.agrestina.domain.product.Product;
import com.agrestina.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductId(Long productId);

    @Query("SELECT i FROM Inventory i WHERE i.id = :id AND i.product.active=true")
    Inventory find(Long id);

    @Query("SELECT i FROM Inventory i WHERE i.product.active=true")
    List<Inventory> findActiveInventories();

    @Query("SELECT i FROM Inventory i WHERE i.product.active=false")
    List<Inventory> findInactiveInventories();

    @Query("SELECT i.product FROM Inventory i WHERE i.quantity = 0")
    List<Product> productsOutOfStock();

}
