package com.agrestina.service;

import com.agrestina.domain.promotions.Promotion;
import com.agrestina.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class PromotionService {

        @Autowired
        private PromotionRepository promotionRepository;

        public Page<Promotion> getAllPromotions(Pageable pagination) {
            return promotionRepository.findAllPromotions(pagination);
        }

        public Promotion getPromotionById(Long id) {
            return promotionRepository.findById(id).orElseThrow(() -> new RuntimeException("Promotion not found"));
        }

        public Promotion createPromotion(Promotion promotion) {
            return promotionRepository.save(promotion);
        }

        public Promotion updatePromotion(Long id, Promotion promotionDetails) {
            Promotion promotion = getPromotionById(id);
            promotion.setName(promotionDetails.getName());
            promotion.setDescription(promotionDetails.getDescription());
            promotion.setDiscountPercentage(promotionDetails.getDiscountPercentage());
            promotion.setStartDate(promotionDetails.getStartDate());
            promotion.setEndDate(promotionDetails.getEndDate());
            promotion.setActive(promotionDetails.isActive());
            return promotionRepository.save(promotion);
        }

        public Promotion deletePromotion(Long id) {
            var promotion = promotionRepository.findById(id).get();
            promotion.disabled();
            return promotion;
        }
}
