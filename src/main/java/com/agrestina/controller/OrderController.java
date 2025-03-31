package com.agrestina.controller;


import com.agrestina.domain.user.User;
import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersByClientId(Pageable pagination, @PathVariable String Id, @AuthenticationPrincipal User user){
        var orders = this.service.listOrdersByClientId(pagination, Id);
        log.info("Listagem de pedidos por Id de cliente realizada com sucesso pelo usuário {} \n {}", user, Id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrderById(Pageable pagination, @PathVariable Long Id, @AuthenticationPrincipal User user){
        var orders = this.service.listOrderById(pagination, Id);
        log.info("Listagem de pedido por Id ({}) realizada com sucesso pelo usuário {}", Id, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("client/name/{Name}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersByClientName(Pageable pagination, @PathVariable String Name, @AuthenticationPrincipal User user){
        var orders = this.service.listOrdersByClientName(pagination, Name);
        log.info("Listagem de pedidos por nome de cliente ({}) realizada com sucesso pelo usuário {}", Name, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("seller/id/{Id}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersBySellerId(Pageable pagination, @PathVariable String Id, @AuthenticationPrincipal User user){
        var orders = this.service.listOrdersBySellerId(pagination, Id);
        log.info("Listagem de pedidos por Id ({}) de vendedor realizada com sucesso pelo usuário {}", Id, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("seller/name/{Name}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersBySellerName(Pageable pagination, @PathVariable String Name, @AuthenticationPrincipal User user){
        var orders = this.service.listOrdersBySellerName(pagination, Name);
        log.info("Listagem de pedidos por nome de vendedor ({}) realizada com sucesso pelo usuário {}", Name, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ResponseOrderDTO>> ListAllOrders(Pageable pagination, @AuthenticationPrincipal User user){
        var orders = this.service.listAllOrders(pagination);
        log.info("Listagem de todos os pedidos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{date}")
    public ResponseEntity<Page<ResponseOrderDTO>> ListOrdersByDate(Pageable pagination, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @AuthenticationPrincipal User user){
        var orders = this.service.listOrdersByDate(pagination, date);
        log.info("Listagem de pedidos por data ({}) realizada com sucesso pelo usuário {}", date, user);
        return ResponseEntity.ok(orders);
    }
}
