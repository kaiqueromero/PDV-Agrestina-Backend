package com.agrestina.domain.order;

import com.agrestina.domain.client.Client;
import com.agrestina.domain.orderedItems.PendingOrderedItem;
import com.agrestina.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pending_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PendingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date = LocalDate.now();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pendingOrder")
    private List<PendingOrderedItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.EM_ANDAMENTO;

    @Column(name = "total_value")
    private double totalValue;

    @Builder
    public PendingOrder(List<PendingOrderedItem> items, User user, Client client) {
        if (items == null || user == null || client == null) {
            throw new IllegalArgumentException("Parameters must not be null");
        }
        this.date = LocalDate.now();
        this.items = items;
        this.items.forEach(i -> i.setPendingOrder(this));
        this.user = user;
        this.client = client;
    }

    public void addItem(PendingOrderedItem item) {
        this.items.add(item);
        item.setPendingOrder(this);
        this.totalValue += item.getTotal();
    }

    public void removeItem(PendingOrderedItem item) {
        this.items.remove(item);
        item.setPendingOrder(null);
        this.totalValue -= item.getTotal();
    }
}