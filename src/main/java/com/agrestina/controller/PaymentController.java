package com.agrestina.controller;

import com.agrestina.dto.payment.PaymentDTO;
import com.agrestina.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order/{orderId}")
    public ResponseEntity<?> processPayment(@PathVariable Long orderId, @RequestBody PaymentDTO paymentDTO) {
        if (orderId == null) {
            return ResponseEntity.badRequest().body("Order ID must not be null");
        }
        try {
            var paymentResponse = this.paymentService.processPayment(orderId, paymentDTO);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}