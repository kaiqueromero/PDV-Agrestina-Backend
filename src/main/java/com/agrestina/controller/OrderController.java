package com.agrestina.controller;


import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/finishedOrders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("client/id/{Id}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersByClientId(Pageable pagination, @PathVariable String Id){
        var orders = this.service.listOrdersByClientId(pagination, Id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrderById(Pageable pagination, @PathVariable Long Id){
        var orders = this.service.listOrderById(pagination, Id);
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
