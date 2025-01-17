package com.agrestina.service;

import com.agrestina.domain.order.Order;
import com.agrestina.domain.order.OrderStatus;
import com.agrestina.domain.payment.Payment;
import com.agrestina.domain.payment.PaymentMethod;
import com.agrestina.dto.payment.PaymentDTO;
import com.agrestina.dto.payment.ResponsePaymentDTO;
import com.agrestina.repository.OrderRepository;
import com.agrestina.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public ResponsePaymentDTO processPayment(Long orderId, PaymentDTO paymentDTO) {
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

        return new ResponsePaymentDTO(payment);
    }

    public Page<ResponsePaymentDTO> listAllPayments(Pageable pagination) {
        return paymentRepository.findAllPayments(pagination).map(ResponsePaymentDTO::new);
    }

    public Page<ResponsePaymentDTO> listPaymentsByDate(Pageable pagination, LocalDate date) {
        return paymentRepository.findPaymentsByDate(pagination, date).map(ResponsePaymentDTO::new);
    }

    public Page<ResponsePaymentDTO> listPaymentsById(Pageable pagination, String id) {
        return paymentRepository.findPaymentsById(pagination, id).map(ResponsePaymentDTO::new);
    }

    public Page<ResponsePaymentDTO> listPaymentsByOrderId(Pageable pagination, Long orderId) {
        return paymentRepository.findPaymentsByOrderId(pagination, orderId).map(ResponsePaymentDTO::new);
    }

    public Page<ResponsePaymentDTO> listPaymentsByClientId(Pageable pagination, String clientId) {
        return paymentRepository.findPaymentsByClientId(pagination, clientId).map(ResponsePaymentDTO::new);
    }

    public Page<ResponsePaymentDTO> listPaymentsByClientName(Pageable pagination, String clientName) {
        return paymentRepository.findPaymentsByClientName(pagination, clientName).map(ResponsePaymentDTO::new);
    }

    public Page<ResponsePaymentDTO> listPaymentsByMethod(Pageable pagination, PaymentMethod method) {
        return paymentRepository.findPaymentsByMethod(pagination, method).map(ResponsePaymentDTO::new);
    }
}