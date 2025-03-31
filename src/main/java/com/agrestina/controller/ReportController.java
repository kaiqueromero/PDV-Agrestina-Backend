package com.agrestina.controller;
import com.agrestina.domain.user.User;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<InventoryReport> getInfoInventory(@AuthenticationPrincipal User user) {
        var report = service.infoInventory();
        log.info("Report de inventário gerado com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getInfoRevenue(@AuthenticationPrincipal User user) {
        var report = service.infoDailyRevenue(LocalDate.now());
        log.info("Report de receita diária gerado com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/billing/category")
    public ResponseEntity<String> getInfoBillingByCategory(@RequestParam("startDate") String startDate,
                                                                     @RequestParam("endDate") String endDate, Model model, @AuthenticationPrincipal User user) {
        var start = LocalDate.parse(startDate);
        var end = LocalDate.parse(endDate);
        var report = service.revenueObtainedByCategory(start, end);
        model.addAttribute("reportByCategory", report);
        log.info("Report de faturamento por categoria gerado com sucesso pelo usuário {}", user);
        return ResponseEntity.ok("getInfoBillingByCategory");
    }

    @GetMapping("/billing/products")
    public ResponseEntity<String> getInfoBillingByProduct(@RequestParam("startDate") String startDate,
                                        @RequestParam("endDate") String endDate, Model model, @AuthenticationPrincipal User user) {
        var start = LocalDate.parse(startDate);
        var end = LocalDate.parse(endDate);
        var report = service.revenueObtainedByProduct(start, end);
        model.addAttribute("reportByProducts", report);
        log.info("Report de faturamento por produto gerado com sucesso pelo usuário {}", user);
        return ResponseEntity.ok("getInfoBillingByProduct");
    }

    @GetMapping("/email")
    public ResponseEntity<String> sendEmail(@AuthenticationPrincipal User user) {
        var inventory = service.infoInventory();
        var billing = service.infoDailyRevenue(LocalDate.now());
        email.send(inventory, billing);
        log.info("Relatório enviado por email com sucesso pelo usuário {}", user);
        return ResponseEntity.ok("Email enviado");
    }
}
