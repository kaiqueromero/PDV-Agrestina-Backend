package com.agrestina.repository;

import com.agrestina.domain.promotions.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
