package com.agrestina.controller;

import com.agrestina.domain.payment.PaymentMethod;
import com.agrestina.dto.payment.PaymentDTO;
import com.agrestina.dto.payment.ResponsePaymentDTO;
import com.agrestina.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping("/all")
    public ResponseEntity<Page<ResponsePaymentDTO>> listAllPayments(Pageable pagination) {
        var payment = this.paymentService.listAllPayments(pagination);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByDate(Pageable pagination, @PathVariable LocalDate date) {
        var payment = this.paymentService.listPaymentsByDate(pagination, date);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsById(Pageable pagination, @PathVariable String id) {
        var payment = this.paymentService.listPaymentsById(pagination, id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByOrderId(Pageable pagination, @PathVariable Long orderId) {
        var payment = this.paymentService.listPaymentsByOrderId(pagination, orderId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/clientId/{clientId}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByClientId(Pageable pagination, @PathVariable String clientId) {
        var payment = this.paymentService.listPaymentsByClientId(pagination, clientId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/clientName/{clientName}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByClientName(Pageable pagination, @PathVariable String clientName) {
        var payment = this.paymentService.listPaymentsByClientName(pagination, clientName);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByMethod(Pageable pagination, @PathVariable PaymentMethod method) {
        var payment = this.paymentService.listPaymentsByMethod(pagination, method);
        return ResponseEntity.ok(payment);
    }
}