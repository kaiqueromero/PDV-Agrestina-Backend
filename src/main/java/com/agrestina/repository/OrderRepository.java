package com.agrestina.repository;

import com.agrestina.domain.order.Order;
import com.agrestina.dto.statistics.ProductsStatistics;
import com.agrestina.dto.statistics.SalesStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.date = :date")
    Page<Order> findOrderByDate(Pageable pageable, LocalDate date);

    @Query("SELECT o FROM Order o WHERE o.client.name = :clientName")
    Page<Order> findOrdersByClientName(Pageable pageable, String clientName);

    @Query("SELECT o FROM Order o WHERE o.client.id = :clientId")
    Page<Order> findOrdersByClientId(Pageable pageable, String clientId);

    @Query("SELECT o FROM Order o WHERE o.user.name = :sellerName")
    Page<Order> findOrdersBySellerName(Pageable pageable, String sellerName);

    @Query("SELECT o FROM Order o WHERE o.user.id = :sellerId")
    Page<Order> findOrdersBySellerId(Pageable pageable, String sellerId);

    @Query("SELECT o FROM Order o")
    Page<Order> findAllOrders(Pageable pageable);

    @Query("""
            SELECT SUM(i.unitPrice * i.quantity)
            FROM Order o
            JOIN o.items i
            WHERE o.date = :date
            """)
   BigDecimal TotalDailyRevenue(LocalDate date);


    @Query("""
        SELECT NEW com.agrestina.dto.statistics.SalesStatistics(
            prod.category,
            SUM(i.quantity),
            SUM(i.unitPrice * i.quantity)
        )
        FROM Order o
        JOIN o.items i
        JOIN i.product prod
        WHERE o.date = :date
        GROUP BY prod.category
        """)
    List<SalesStatistics> TotalDailyRevenueByCategory(LocalDate date);

//    @Query("""
//            SELECT SUM(i.unitPrice * i.quantity)
//            FROM Order o
//            JOIN o.items i
//            WHERE o.date = :date
//            GROUP BY i.product.name
//            """)
//    BigDecimal TotalProductsRevenue(LocalDate date);

    @Query("SELECT NEW com.agrestina.dto.statistics.ProductsStatistics(" +
            "prod.name, " +
            "SUM(i.quantity), " +
            "SUM(i.unitPrice * i.quantity)) " +
            "FROM Order o " +
            "JOIN o.items i " +
            "JOIN i.product prod " +
            "WHERE o.date = :date " +
            "GROUP BY prod.name")
    List<ProductsStatistics> TotalDailyRevenueByProducts(LocalDate date);
}
