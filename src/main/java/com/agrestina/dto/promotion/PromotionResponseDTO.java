//package com.agrestina.dto.promotion;
//
//import com.agrestina.domain.promotions.Promotion;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//public record PromotionResponseDTO(Long id, String name, String description, BigDecimal discountPercentage, LocalDate startDate, LocalDate endDate, Boolean active) {
//    public PromotionResponseDTO(Promotion promotion) {
//        this(promotion.getId(), promotion.getName(), promotion.getDescription(), promotion.getDiscountPercentage(), promotion.getStartDate(), promotion.getEndDate(), promotion.isActive());
//    }
//}
