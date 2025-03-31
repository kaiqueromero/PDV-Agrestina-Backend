//package com.agrestina.dto.promotion;
//
//import com.agrestina.domain.promotions.Promotion;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//public record PromotionRequestDTO(String name, String description, BigDecimal discountPercentage, LocalDate startDate, LocalDate endDate, Boolean active) {
//    public PromotionRequestDTO(Promotion promotion) {
//        this(promotion.getName(), promotion.getDescription(), promotion.getDiscountPercentage(), promotion.getStartDate(), promotion.getEndDate(), promotion.isActive());
//    }
//}