package com.agrestina.service;

import com.agrestina.domain.client.Client;
import com.agrestina.domain.inventory.Inventory;
import com.agrestina.domain.order.Order;
import com.agrestina.domain.order.OrderStatus;
import com.agrestina.domain.order.PendingOrder;
import com.agrestina.domain.orderedItems.OrderedItem;
import com.agrestina.domain.orderedItems.PendingOrderedItem;
import com.agrestina.domain.product.Product;
import com.agrestina.domain.user.User;
import com.agrestina.dto.order.OrderedItemDTO;
import com.agrestina.dto.order.RegisterOrderDTO;
import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.dto.order.ResponsePendingOrderDTO;
import com.agrestina.exeption.ValidationException;
import com.agrestina.mail.OrderRealizedEmail;
import com.agrestina.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PendingOrderedItemsRepository pendingOrderedItemsRepository;
    @Autowired
    private OrderedItemsRepository orderedItemsRepository;
    @Autowired
    private OrderRealizedEmail email;
    @Autowired
    private PendingOrderRepository pendingOrderRepository;

    @Transactional
    public ResponsePendingOrderDTO register(RegisterOrderDTO dto, String login) {

        User user = userRepository.findUser(login)
                .orElseThrow(() -> new ValidationException("Usuário autenticado não encontrado!"));

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        PendingOrder pendingOrder = new PendingOrder();

        var items = dto.items().stream()
                .map(itemDto -> {
                    var inventory = inventoryRepository.findByProductId(itemDto.productId());
                    validateInventory(inventory, itemDto.quantity(), itemDto.productId());
                    var product = productRepository.findById(itemDto.productId())
                            .orElseThrow(() -> new ValidationException(
                                    getErrorMessage("product.notfound", itemDto.productId())));
                    validateProduct(product);
                    inventory.decrease(itemDto.quantity());
                    return new PendingOrderedItem(pendingOrder, product, itemDto.quantity());
                })
                .collect(Collectors.toList());

        var total = items.stream().mapToDouble(PendingOrderedItem::getTotal).sum();

        pendingOrder.setDate(LocalDate.now());
        pendingOrder.setItems(items);
        pendingOrder.setUser(user);
        pendingOrder.setClient(client);
        pendingOrder.setTotalValue(total);
        pendingOrder.setStatus(OrderStatus.EM_ANDAMENTO);
        pendingOrderRepository.save(pendingOrder);
        pendingOrderedItemsRepository.saveAll(items);
        return new ResponsePendingOrderDTO(pendingOrder);
    }

    @Transactional
    public ResponseOrderDTO finalizeOrder(Long pendingOrderId) {
        PendingOrder pendingOrder = pendingOrderRepository.findById(pendingOrderId)
                .orElseThrow(() -> new RuntimeException("Pending order not found"));

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
        pendingOrderRepository.delete(pendingOrder);
        return new ResponseOrderDTO(order);
    }

    @Transactional
    public void addItemsToPendingOrder(Long pendingOrderId, List<OrderedItemDTO> itemsDto) {
        PendingOrder pendingOrder = pendingOrderRepository.findById(pendingOrderId)
                .orElseThrow(() -> new RuntimeException("Pending order not found"));

        List<PendingOrderedItem> items = itemsDto.stream()
                .map(itemDto -> {
                    var inventory = inventoryRepository.findByProductId(itemDto.productId());
                    validateInventory(inventory, itemDto.quantity(), itemDto.productId());
                    var product = productRepository.findById(itemDto.productId())
                            .orElseThrow(() -> new ValidationException(
                                    getErrorMessage("product.notfound", itemDto.productId())));
                    validateProduct(product);
                    inventory.decrease(itemDto.quantity());
                    return new PendingOrderedItem(pendingOrder, product, itemDto.quantity());
                })
                .collect(Collectors.toList());

        items.forEach(pendingOrder::addItem);
        pendingOrderRepository.save(pendingOrder);
        pendingOrderedItemsRepository.saveAll(items);
    }

    @Transactional
    public void removeItemsFromPendingOrder(Long pendingOrderId, List<OrderedItemDTO> itemsDto) {
        PendingOrder pendingOrder = pendingOrderRepository.findById(pendingOrderId)
                .orElseThrow(() -> new RuntimeException("Pending order not found"));

        itemsDto.forEach(itemDto -> {
            PendingOrderedItem item = pendingOrderedItemsRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            if (item.getQuantity() > itemDto.quantity()) {
                item.setQuantity(item.getQuantity() - itemDto.quantity());
                pendingOrderedItemsRepository.save(item);
            } else {
                pendingOrder.removeItem(item);
                pendingOrderedItemsRepository.delete(item);
            }
        });

        pendingOrderRepository.save(pendingOrder);
    }

    private void validateInventory(Inventory inventory, int quantity, Long productId) {
        if (inventory.getQuantity() < quantity) {
            throw new ValidationException(getErrorMessage("inventory.unavailable", productId));
        }
    }

    private void validateProduct(Product product) {
        if (!product.isActive()) {
            throw new ValidationException(getErrorMessage("product.inactive", product.getId()));
        }
    }

    private String getErrorMessage(String key, Object... args) {
        return String.format(key, args);
    }

    public Page<ResponseOrderDTO> listOrdersByClientName(Pageable pagination, String clientName) {
        return repository.findOrdersByClientName(pagination, clientName).map(ResponseOrderDTO::new);
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

    public Page<ResponsePendingOrderDTO> listAllPendingOrders(Pageable pagination) {
        return pendingOrderRepository.findAllPendingOrders(pagination).map(ResponsePendingOrderDTO::new);
    }
}