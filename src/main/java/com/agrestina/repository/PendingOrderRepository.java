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
}
