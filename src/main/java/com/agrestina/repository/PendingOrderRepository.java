package com.agrestina.repository;

import com.agrestina.domain.order.PendingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {

    @Query("SELECT o FROM PendingOrder o")
    Page<PendingOrder> findAllPendingOrders(Pageable pageable);

    @Query("SELECT o FROM PendingOrder o WHERE o.id = :id")
    Page<PendingOrder> findPendingOrderById(Pageable pageable, Long id);

    @Query("SELECT o FROM PendingOrder o WHERE o.date = :date")
    Page<PendingOrder> findPendingOrdersByDate(Pageable pageable, LocalDate date);

    @Query("SELECT o FROM PendingOrder o WHERE o.client.name = :clientName")
    Page<PendingOrder> findPendingOrderByClientName(Pageable pageable, String clientName);

    @Query("SELECT o FROM PendingOrder o WHERE o.client.id = :clientId")
    Page<PendingOrder> findPendingOrderByClientId(Pageable pageable, String clientId);

    @Query("SELECT o FROM PendingOrder o WHERE o.user.name = :sellerName")
    Page<PendingOrder> findPendingOrdersBySellerName(Pageable pageable, String sellerName);

    @Query("SELECT o FROM PendingOrder o WHERE o.user.id = :sellerId")
    Page<PendingOrder> findPendingOrdersBySellerId(Pageable pageable, String sellerId);

}
