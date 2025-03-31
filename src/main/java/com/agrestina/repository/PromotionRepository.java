package com.agrestina.repository;

import com.agrestina.domain.product.Product;
import com.agrestina.domain.promotions.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("SELECT p FROM Promotion p")
    Page<Promotion> findAllPromotions(Pageable pageable);
}
