package com.agrestina.service;

import com.agrestina.dto.product.ProductDTO;
import com.agrestina.dto.statistics.BillingReport;
import com.agrestina.dto.statistics.InventoryReport;
import com.agrestina.dto.statistics.ProductReport;
import com.agrestina.dto.statistics.SalesStatistics;
import com.agrestina.repository.InventoryRepository;
import com.agrestina.repository.OrderRepository;
import com.agrestina.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class ReportService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    public InventoryReport infoInventory(){
        var outOfStockProduct = inventoryRepository.productsOutOfStock()
                .stream().map(ProductDTO::new)
                .toList();
        return new InventoryReport(outOfStockProduct);
    }

    public BigDecimal  infoDailyRevenue(LocalDate date) {
        BigDecimal revenue = orderRepository.TotalDailyRevenue(date);
        return revenue;
    }

    public ProductReport revenueObtainedByProduct(LocalDate startDate, LocalDate endDate) {
        var totalRevenue = orderRepository.TotalRevenue(startDate, endDate);
        var statistics = orderRepository.TotalDailyRevenueByProducts(startDate, endDate);
        return new ProductReport(totalRevenue, statistics);
    }

    public BillingReport revenueObtainedByCategory(LocalDate startDate, LocalDate endDate) {
        var totalRevenue = orderRepository.TotalRevenue(startDate, endDate);
        var statistics = orderRepository.TotalDailyRevenueByCategory(startDate, endDate);
        return new BillingReport(totalRevenue, statistics);
    }
}
