package com.agrestina.repository;

import com.agrestina.domain.payment.Payment;
import com.agrestina.domain.payment.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    @Query("SELECT p FROM Payment p")
    Page<Payment> findAllPayments(Pageable pagination);

    @Query("SELECT p FROM Payment p WHERE p.paymentDate = :date")
    Page<Payment> findPaymentsByDate(Pageable pagination, LocalDate date);

    @Query("SELECT p FROM Payment p WHERE p.id = :id")
    Page<Payment> findPaymentsById(Pageable pagination, String id);

    @Query("SELECT p FROM Payment p WHERE p.order.id = :orderId")
    Page<Payment> findPaymentsByOrderId(Pageable pagination, Long orderId);

    @Query("SELECT p FROM Payment p WHERE p.order.client.id = :clientId")
    Page<Payment> findPaymentsByClientId(Pageable pagination, String clientId);

    @Query("SELECT p FROM Payment p WHERE p.order.client.name = :clientName")
    Page<Payment> findPaymentsByClientName(Pageable pagination, String clientName);

    @Query("SELECT p FROM Payment p WHERE p.method = :method")
    Page<Payment> findPaymentsByMethod(Pageable pagination, PaymentMethod method);
}