package com.agrestina.repository;

import com.agrestina.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository <Product, Long> {

    boolean existsByNameIgnoringCase(String name);
    @Query("SELECT p FROM Product p WHERE p.active=true")
    Page<Product> findActive(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.active=false")
    Page<Product> findInactive(Pageable pageable);

    @Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Page<Product> findProductsByName(Pageable pageable, String name);

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Page<Product> findProductsById(Pageable pageable, String id);
}