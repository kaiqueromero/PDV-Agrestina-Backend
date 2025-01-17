package com.agrestina.service;

import com.agrestina.domain.client.Client;
import com.agrestina.domain.inventory.Inventory;
import com.agrestina.domain.order.Order;
import com.agrestina.domain.order.OrderStatus;
import com.agrestina.domain.orderedItems.OrderedItem;
import com.agrestina.domain.product.Product;
import com.agrestina.domain.user.User;
import com.agrestina.dto.order.ResponseOrderDTO;
import com.agrestina.dto.order.RegisterOrderDTO;
import com.agrestina.exeption.ValidationException;
import com.agrestina.mail.OrderRealizedEmail;
import com.agrestina.repository.*;
import jakarta.transaction.Transactional;
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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    OrderedItemsRepository orderedItemsRepository;
    @Autowired
    private OrderRealizedEmail email;

    @Transactional
    public ResponseOrderDTO register(RegisterOrderDTO dto, String login) {

        User user = userRepository.findUser(login)
                .orElseThrow(() -> new ValidationException("Usuário autenticado não encontrado!"));

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Order order = new Order();

        var items = dto.items().stream()
                .map(itemDto -> {
                    var inventory = inventoryRepository.findByProductId(itemDto.productId());
                    validateInventory(inventory, itemDto.quantity(), itemDto.productId());
                    var product = productRepository.findById(itemDto.productId())
                            .orElseThrow(() -> new ValidationException(
                                    getErrorMessage("product.notfound", itemDto.productId())));
                    validateProduct(product);
                    inventory.decrease(itemDto.quantity());
                    return new OrderedItem(order, product, itemDto.quantity());
                })
                .collect(Collectors.toList());

        var total = items.stream().mapToDouble(OrderedItem::getTotal).sum();

        order.setDate(LocalDate.now());
        order.setItems(items);
        order.setUser(user);
        order.setClient(client);
        order.setTotalValue(total);
        order.setStatus(OrderStatus.FINALIZADO);
        client.addOrder(order);
        repository.save(order);
        orderedItemsRepository.saveAll(items);
        email.send(order);
        return new ResponseOrderDTO(order);
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
}
