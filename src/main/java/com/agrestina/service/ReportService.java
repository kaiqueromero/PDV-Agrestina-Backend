package com.agrestina.service;

import com.agrestina.dto.product.ProductDTO;
import com.agrestina.dto.statistics.BillingReport;
import com.agrestina.dto.statistics.InventoryReport;
import com.agrestina.repository.InventoryRepository;
import com.agrestina.repository.OrderRepository;
import com.agrestina.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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

    public BillingReport revenueObtained(LocalDate date){
        var totalRevenue = orderRepository.TotalDailyRevenue(date);

        var statistics = orderRepository.TotalDailyRevenueByCategory(date);

        return new BillingReport(totalRevenue, statistics);
    }
}
