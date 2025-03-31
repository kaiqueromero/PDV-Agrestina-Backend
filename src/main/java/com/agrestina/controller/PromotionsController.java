package com.agrestina.controller;

import com.agrestina.domain.promotions.Promotion;
import com.agrestina.domain.user.User;
import com.agrestina.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/promotions")
public class PromotionsController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public ResponseEntity<Page<Promotion>> getAllPromotions(Pageable pagination, @AuthenticationPrincipal User user) {
        log.info("Listagem de todas as promoções realizada com sucesso pelo usuário {}", user);
        var promotions = promotionService.getAllPromotions(pagination);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Listagem de promoção por Id ({}) realizada com sucesso pelo usuário {}", id, user);
        var promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion, @AuthenticationPrincipal User user) {
        log.info("Nova promoção criada com sucesso pelo usuário {} ", user );
        var newPromotion = promotionService.createPromotion(promotion);
        return ResponseEntity.ok(newPromotion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, @RequestBody Promotion promotionDetails, @AuthenticationPrincipal User user) {
        log.info("Promoção ({}) atualizada com sucesso pelo usuário {}", id, user);
        var promotion =  promotionService.updatePromotion(id, promotionDetails);
        return ResponseEntity.ok(promotion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Promotion> deletePromotion(@PathVariable Long id, @AuthenticationPrincipal User user) {
        var promotion = promotionService.deletePromotion(id);
        log.info("Promoção ({}) deletada com sucesso pelo usuário {}", id, user);
        return ResponseEntity.ok(promotion);
    }
}