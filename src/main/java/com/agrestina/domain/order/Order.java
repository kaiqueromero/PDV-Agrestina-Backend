package com.agrestina.domain.order;


import com.agrestina.domain.client.Client;
import com.agrestina.domain.orderedItems.OrderedItem;
import com.agrestina.domain.payment.Payment;
import com.agrestina.domain.user.User;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date = LocalDate.now();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderedItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.INICIADO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @Column(name = "total_value")
    private double totalValue;

    @Builder
    public Order(List<OrderedItem> items, User user, Client client) {
        if (items == null || user == null || client == null) {
            throw new IllegalArgumentException("Parameters must not be null");
        }
        this.date = LocalDate.now();
        this.items = items;
        this.items.forEach(i -> i.setOrder(this));
        this.user = user;
        this.client = client;
    }

    public void addItem(OrderedItem item) {
        this.items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderedItem item) {
        this.items.remove(item);
        item.setOrder(null);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setOrder(this);
    }

    public void removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setOrder(null);
    }
}
