package com.agrestina.service;

import com.agrestina.domain.order.Order;
import com.agrestina.domain.order.OrderStatus;
import com.agrestina.domain.order.PendingOrder;
import com.agrestina.domain.orderedItems.OrderedItem;
import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public ResponseOrderDTO saveOrder(PendingOrder pendingOrder) {

        Order order = new Order();
        order.setDate(pendingOrder.getDate());
        order.setItems(pendingOrder.getItems().stream()
                .map(pendingItem -> new OrderedItem(order, pendingItem.getProduct(), pendingItem.getQuantity()))
                .collect(Collectors.toList()));
        order.setUser(pendingOrder.getUser());
        order.setClient(pendingOrder.getClient());
        order.setTotalValue(pendingOrder.getTotalValue());
        order.setStatus(OrderStatus.FINALIZADO);

        repository.save(order);
        return new ResponseOrderDTO(order);
    }

    public Page<ResponseOrderDTO> listOrdersByClientName(Pageable pagination, String clientName) {
        return repository.findOrdersByClientName(pagination, clientName).map(ResponseOrderDTO::new);
    }

    public Page<ResponseOrderDTO> listOrderById(Pageable pagination, Long id) {
        return repository.findOrderById(pagination, id).map(ResponseOrderDTO::new);
    }

    public Page<ResponseOrderDTO> listOrdersByClientId(Pageable pagination, String clientId) {
        return repository.findOrdersByClientId(pagination, clientId).map(ResponseOrderDTO::new);
    }

    public Page<ResponseOrderDTO> listOrdersBySellerName(Pageable pagination, String sellerName) {
        return repository.findOrdersBySellerName(pagination, sellerName).map(ResponseOrderDTO::new);
    }

    public Page<ResponseOrderDTO> listOrdersBySellerId(Pageable pagination, String sellerId) {
        return repository.findOrdersBySellerId(pagination, sellerId).map(ResponseOrderDTO::new);
    }

    public Page<ResponseOrderDTO> listAllOrders(Pageable pagination) {
        return repository.findAllOrders(pagination).map(ResponseOrderDTO::new);
    }

    public Page<ResponseOrderDTO> listOrdersByDate(Pageable pagination, LocalDate date) {
        return repository.findOrderByDate(pagination, date).map(ResponseOrderDTO::new);
    }
}