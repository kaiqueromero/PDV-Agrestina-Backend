package com.agrestina.service;

import com.agrestina.domain.order.Order;
import com.agrestina.domain.order.OrderStatus;
import com.agrestina.domain.payment.Payment;
import com.agrestina.domain.payment.PaymentMethod;
import com.agrestina.dto.payment.PaymentDTO;
import com.agrestina.repository.OrderRepository;
import com.agrestina.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public PaymentDTO processPayment(Long orderId, PaymentDTO paymentDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        Payment payment = Payment.builder()
                .order(order)
                .method(paymentDTO.method())
                .paymentDate(paymentDTO.paymentDate())
                .receipt(paymentDTO.receipt())
                .build();

        paymentRepository.save(payment);

        order.addPayment(payment);
        if (order.getPayments().stream().anyMatch(p -> p.getMethod() == PaymentMethod.A_PRAZO || p.getMethod() == PaymentMethod.CHEQUE)) {
            order.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        } else {
            order.setStatus(OrderStatus.PAGO);
        }
        orderRepository.save(order);

        return new PaymentDTO(payment);
    }
}
