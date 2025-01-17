package com.agrestina.controller;


import com.agrestina.domain.user.User;
import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.dto.order.RegisterOrderDTO;
import com.agrestina.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<ResponseOrderDTO> register(@Valid @RequestBody RegisterOrderDTO dto,
                                                     @AuthenticationPrincipal User user) {
        var order = this.service.register(dto, user.getLogin());
        return ResponseEntity.ok(order);
    }

    @GetMapping("client/id/{Id}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersByClientId(Pageable pagination, @PathVariable String Id){
        var orders = this.service.listOrdersByClientId(pagination, Id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("client/name/{Name}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersByClientName(Pageable pagination, @PathVariable String Name){
        var orders = this.service.listOrdersByClientName(pagination, Name);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("seller/id/{Id}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersBySellerId(Pageable pagination, @PathVariable String Id){
        var orders = this.service.listOrdersBySellerId(pagination, Id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("seller/name/{Name}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersBySellerName(Pageable pagination, @PathVariable String Name){
        var orders = this.service.listOrdersBySellerName(pagination, Name);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ResponseOrderDTO>> ListAllOrders(Pageable pagination){
        var orders = this.service.listAllOrders(pagination);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{date}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersByDate(Pageable pagination, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        var orders = this.service.listOrdersByDate(pagination, date);
        return ResponseEntity.ok(orders);
    }
}
