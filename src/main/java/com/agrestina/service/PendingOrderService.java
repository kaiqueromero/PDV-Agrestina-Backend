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
import com.agrestina.dto.inventory.UpdateInventoryDTO;
import com.agrestina.dto.order.OrderedItemDTO;
import com.agrestina.dto.order.RegisterOrderDTO;
import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.dto.order.ResponsePendingOrderDTO;
import com.agrestina.exeption.ValidationException;
import com.agrestina.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PendingOrderService {

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
    private InventoryService inventoryService;
    @Autowired
    private PendingOrderRepository pendingOrderRepository;
    @Autowired
    private OrderService orderService;

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

        var total = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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
        var response = orderService.saveOrder(pendingOrder);
        pendingOrderRepository.delete(pendingOrder);
        return response;
    }

    @Transactional
    public List<PendingOrderedItem> addItemsToPendingOrder(Long pendingOrderId, List<OrderedItemDTO> itemsDto) {
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
        return pendingOrder.getItems();
    }


    @Transactional
    public List<PendingOrderedItem> removeItemsFromPendingOrder(Long pendingOrderId, List<OrderedItemDTO> itemsDto) {
        PendingOrder pendingOrder = pendingOrderRepository.findById(pendingOrderId)
                .orElseThrow(() -> new RuntimeException("Pending order not found"));

        itemsDto.forEach(itemDto -> {
            PendingOrderedItem item = pendingOrderedItemsRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            BigDecimal itemTotalValue = item.getProduct().getPrice().multiply(BigDecimal.valueOf(itemDto.quantity()));

            if (item.getQuantity() > itemDto.quantity()) {
                item.setQuantity(item.getQuantity() - itemDto.quantity());
                pendingOrderedItemsRepository.save(item);
            } else {
                pendingOrder.removeItem(item);
                pendingOrderedItemsRepository.delete(item);
            }

            inventoryService.updateInventory(new UpdateInventoryDTO(item.getProduct().getId(), itemDto.quantity()));
            pendingOrder.setTotalValue(pendingOrder.getTotalValue().subtract(itemTotalValue));
        });

        pendingOrderRepository.save(pendingOrder);
        return pendingOrder.getItems();
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

    public Page<ResponsePendingOrderDTO> listAllPendingOrders(Pageable pagination) {
        return pendingOrderRepository.findAllPendingOrders(pagination).map(ResponsePendingOrderDTO::new);
    }

    public Page<ResponsePendingOrderDTO> listPendingOrderById(Pageable pagination, Long id) {
        return pendingOrderRepository.findPendingOrderById(pagination, id).map(ResponsePendingOrderDTO::new);
    }

    public Page<ResponsePendingOrderDTO> listPendingOrderByClientName(Pageable pagination, String clientName) {
        return  pendingOrderRepository.findPendingOrderByClientName(pagination, clientName).map(ResponsePendingOrderDTO::new);
    }

    public Page<ResponsePendingOrderDTO> listPendingOrderByClientId(Pageable pagination, String clientId) {
        return pendingOrderRepository.findPendingOrderByClientId(pagination, clientId).map(ResponsePendingOrderDTO::new);
    }

    public Page<ResponsePendingOrderDTO> listPendingOrdersBySellerName(Pageable pagination, String sellerName) {
        return pendingOrderRepository.findPendingOrdersBySellerName(pagination, sellerName).map(ResponsePendingOrderDTO::new);
    }

    public Page<ResponsePendingOrderDTO> listPendingOrdersBySellerId(Pageable pagination, String sellerId) {
        return pendingOrderRepository.findPendingOrdersBySellerId(pagination, sellerId).map(ResponsePendingOrderDTO::new);
    }

    public Page<ResponsePendingOrderDTO> listPendingOrdersByDate(Pageable pagination, LocalDate date) {
        return pendingOrderRepository.findPendingOrdersByDate(pagination, date).map(ResponsePendingOrderDTO::new);
    }
}
