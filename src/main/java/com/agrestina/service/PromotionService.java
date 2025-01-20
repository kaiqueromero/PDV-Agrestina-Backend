package com.agrestina.service;

import com.agrestina.domain.promotions.Promotion;
import com.agrestina.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class PromotionService {

        @Autowired
        private PromotionRepository promotionRepository;

        public List<Promotion> getAllPromotions() {
            return promotionRepository.findAll();
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

        public void deletePromotion(Long id) {
            Promotion promotion = getPromotionById(id);
            promotionRepository.delete(promotion);
        }
}
