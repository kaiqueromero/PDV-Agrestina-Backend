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
        return ResponseEntity.ok(order);
    }

    @PostMapping("/finalize/{pendingOrderId}")
    public ResponseEntity<ResponseOrderDTO> finalizeOrder(@PathVariable Long pendingOrderId) {
        var order = this.service.finalizeOrder(pendingOrderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/add-items/{pendingOrderId}")
    public ResponseEntity<List<PendingOrderedItem>> addItemsToPendingOrder(@PathVariable Long pendingOrderId, @Valid @RequestBody List<OrderedItemDTO> itemsDto) {
        var items = this.service.addItemsToPendingOrder(pendingOrderId, itemsDto);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/remove-items/{pendingOrderId}")
    public ResponseEntity<List<PendingOrderedItem>> removeItemsFromPendingOrder(@PathVariable Long pendingOrderId, @Valid @RequestBody List<OrderedItemDTO> itemsDto) {
        var items = this.service.removeItemsFromPendingOrder(pendingOrderId, itemsDto);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/all/pending-orders")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListAllPendingOrders(Pageable pagination){
        var orders = this.service.listAllPendingOrders(pagination);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrderById(Pageable pagination , @PathVariable Long Id){
        var order = this.service.listPendingOrderById(pagination, Id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("client/id/{Id}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrderByClientId(Pageable pagination, @PathVariable String Id){
        var order = this.service.listPendingOrderByClientId(pagination, Id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("client/name/{Name}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrderByClientName(Pageable pagination, @PathVariable String Name){
        var order = this.service.listPendingOrderByClientName(pagination, Name);
        return ResponseEntity.ok(order);
    }

    @GetMapping("seller/id/{Id}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrdersBySellerId(Pageable pagination, @PathVariable String Id){
        var orders = this.service.listPendingOrdersBySellerId(pagination, Id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("seller/name/{Name}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrdersBySellerName(Pageable pagination, @PathVariable String Name){
        var orders = this.service.listPendingOrdersBySellerName(pagination, Name);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{date}")
    public ResponseEntity<Page<ResponsePendingOrderDTO>> ListPendingOrdersByDate(Pageable pagination, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        var orders = this.service.listPendingOrdersByDate(pagination, date);
        return ResponseEntity.ok(orders);
    }
}
