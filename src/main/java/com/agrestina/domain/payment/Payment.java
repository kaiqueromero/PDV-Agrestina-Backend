package com.agrestina.domain.payment;

import com.agrestina.domain.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    private LocalDate paymentDate;
    private String receipt;

    @Builder
    public Payment(Order order, PaymentMethod method, LocalDate paymentDate, String receipt) {
        this.order = order;
        this.method = method;
        this.paymentDate = paymentDate;
        this.receipt = receipt;
    }
}
