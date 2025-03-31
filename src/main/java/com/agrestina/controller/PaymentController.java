package com.agrestina.controller;

import com.agrestina.domain.payment.PaymentMethod;
import com.agrestina.domain.user.User;
import com.agrestina.dto.payment.PaymentDTO;
import com.agrestina.dto.payment.ResponsePaymentDTO;
import com.agrestina.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order/{orderId}")
    public ResponseEntity<?> processPayment(@PathVariable Long orderId, @RequestBody PaymentDTO paymentDTO, @AuthenticationPrincipal User user) {
        if (orderId == null) {
            return ResponseEntity.badRequest().body("Order ID must not be null");
        }
        try {
            var paymentResponse = this.paymentService.processPayment(orderId, paymentDTO);
            log.info("Pagamento do pedido {} processado com sucesso pelo usuário {}", orderId, user);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ResponsePaymentDTO>> listAllPayments(Pageable pagination, @AuthenticationPrincipal User user) {
        var payment = this.paymentService.listAllPayments(pagination);
        log.info("Listagem de todos os pagamentos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByDate(Pageable pagination, @PathVariable LocalDate date, @AuthenticationPrincipal User user) {
        var payment = this.paymentService.listPaymentsByDate(pagination, date);
        log.info("Listagem de pagamentos por data ({}) realizada com sucesso pelo usuário {}", date, user);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsById(Pageable pagination, @PathVariable String id, @AuthenticationPrincipal User user) {
        var payment = this.paymentService.listPaymentsById(pagination, id);
        log.info("Listagem de pagamentos por ID ({}) realizada com sucesso pelo usuário {}", id, user);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByOrderId(Pageable pagination, @PathVariable Long orderId, @AuthenticationPrincipal User user) {
        var payment = this.paymentService.listPaymentsByOrderId(pagination, orderId);
        log.info("Listagem de pagamentos por ID de pedido ({}) realizada com sucesso pelo usuário {}", orderId, user);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/clientId/{clientId}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByClientId(Pageable pagination, @PathVariable String clientId, @AuthenticationPrincipal User user) {
        var payment = this.paymentService.listPaymentsByClientId(pagination, clientId);
        log.info("Listagem de pagamentos por ID de cliente ({}) realizada com sucesso pelo usuário {}", clientId, user);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/clientName/{clientName}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByClientName(Pageable pagination, @PathVariable String clientName, @AuthenticationPrincipal User user) {
        var payment = this.paymentService.listPaymentsByClientName(pagination, clientName);
        log.info("Listagem de pagamentos por nome de cliente ({}) realizada com sucesso pelo usuário {}", clientName, user);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<Page<ResponsePaymentDTO>> listPaymentsByMethod(Pageable pagination, @PathVariable PaymentMethod method, @AuthenticationPrincipal User user) {
        var payment = this.paymentService.listPaymentsByMethod(pagination, method);
        log.info("Listagem de pagamentos por método ({}) realizada com sucesso pelo usuário {}", method, user);
        return ResponseEntity.ok(payment);
    }
}