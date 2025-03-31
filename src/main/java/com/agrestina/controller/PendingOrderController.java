package com.agrestina.controller;

import com.agrestina.domain.orderedItems.PendingOrderedItem;
import com.agrestina.domain.user.User;
import com.agrestina.dto.order.OrderedItemDTO;
import com.agrestina.dto.order.RegisterOrderDTO;
import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.dto.order.ResponsePendingOrderDTO;
import com.agrestina.service.PendingOrderService;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class PendingOrderController {

    @Autowired
    private PendingOrderService service;

    @PostMapping
    public ResponseEntity<ResponsePendingOrderDTO> register(@Valid @RequestBody RegisterOrderDTO dto,
                                                            @AuthenticationPrincipal User user) {
        var order = this.service.register(dto, user.getLogin());
        log.info("Pedido ({}) registrado com sucesso pelo usuário {}", order.id(), user);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/finalize/{pendingOrderId}")
    public ResponseEntity<ResponseOrderDTO> finalizeOrder(@PathVariable Long pendingOrderId, @AuthenticationPrincipal User user) {
        var order = this.service.finalizeOrder(pendingOrderId);
        log.info("Pedido ({}) finalizado com sucesso pelo usuário {}", order.id(), user);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/add-items/{pendingOrderId}")
    public ResponseEntity<List<PendingOrderedItem>> addItemsToPendingOrder(@PathVariable Long pendingOrderId, @Valid @RequestBody List<OrderedItemDTO> itemsDto, @AuthenticationPrincipal User user) {
        var items = this.service.addItemsToPendingOrder(pendingOrderId, itemsDto);
        log.info("Itens adicionados ao pedido ({}) com sucesso pelo usuário {}", pendingOrderId, user);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/remove-items/{pendingOrderId}")
    public ResponseEntity<List<PendingOrderedItem>> removeItemsFromPendingOrder(@PathVariable Long pendingOrderId, @Valid @RequestBody List<OrderedItemDTO> itemsDto, @AuthenticationPrincipal User user) {
        var items = this.service.removeItemsFromPendingOrder(pendingOrderId, itemsDto);
        log.info("Itens removidos do pedido ({}) com sucesso pelo usuário {}", pendingOrderId, user);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/all/pending-orders")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListAllPendingOrders(Pageable pagination, @AuthenticationPrincipal User user){
        var orders = this.service.listAllPendingOrders(pagination);
        log.info("Listagem de todos os pedidos pendentes realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrderById(Pageable pagination , @PathVariable Long Id, @AuthenticationPrincipal User user){
        var order = this.service.listPendingOrderById(pagination, Id);
        log.info("Listagem de pedido por Id ({}) realizada com sucesso pelo usuário {}", Id, user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("client/id/{Id}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrderByClientId(Pageable pagination, @PathVariable String Id, @AuthenticationPrincipal User user){
        var order = this.service.listPendingOrderByClientId(pagination, Id);
        log.info("Listagem de pedidos por Id de cliente ({}) realizada com sucesso pelo usuário {}", Id, user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("client/name/{Name}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrderByClientName(Pageable pagination, @PathVariable String Name, @AuthenticationPrincipal User user){
        var order = this.service.listPendingOrderByClientName(pagination, Name);
        log.info("Listagem de pedidos por nome de cliente ({}) realizada com sucesso pelo usuário {}", Name, user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("seller/id/{Id}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrdersBySellerId(Pageable pagination, @PathVariable String Id, @AuthenticationPrincipal User user){
        var orders = this.service.listPendingOrdersBySellerId(pagination, Id);
        log.info("Listagem de pedidos por Id de vendedor ({}) realizada com sucesso pelo usuário {}", Id, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("seller/name/{Name}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrdersBySellerName(Pageable pagination, @PathVariable String Name, @AuthenticationPrincipal User user){
        var orders = this.service.listPendingOrdersBySellerName(pagination, Name);
        log.info("Listagem de pedidos por nome de vendedor ({}) realizada com sucesso pelo usuário {}", Name, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{date}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrdersByDate(Pageable pagination, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @AuthenticationPrincipal User user){
        var orders = this.service.listPendingOrdersByDate(pagination, date);
        log.info("Listagem de pedidos por data ({}) realizada com sucesso pelo usuário {}", date, user);
        return ResponseEntity.ok(orders);
    }
}
