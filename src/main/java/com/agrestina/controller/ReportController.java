package com.agrestina.controller;
import com.agrestina.dto.statistics.BillingReport;
import com.agrestina.dto.statistics.InventoryReport;
import com.agrestina.dto.statistics.ProductReport;
import com.agrestina.mail.ReportEmail;
import com.agrestina.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    private ReportService service;

    @Autowired
    private ReportEmail email;

    @GetMapping("/inventory")
    public ResponseEntity<InventoryReport> getInfoInventory(){
        var report = service.infoInventory();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/billing/category/{date}")
    public ResponseEntity<BillingReport> getInfoBillingByCategory(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        var report = service.revenueObtainedByCategory(date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/billing/products/{date}")
    public ResponseEntity<ProductReport> getInfoBillingByProducts(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        var report = service.revenueObtainedByProduct(date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/email")
    public ResponseEntity<String> sendEmail(){
        var inventory = service.infoInventory();
        var billing = service.revenueObtainedByCategory(LocalDate.now());
        email.send(inventory, billing);
        return ResponseEntity.ok("Email enviado");
    }
}
