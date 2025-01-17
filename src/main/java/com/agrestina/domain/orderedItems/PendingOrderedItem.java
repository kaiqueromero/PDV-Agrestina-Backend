package com.agrestina.domain.orderedItems;

import com.agrestina.domain.order.PendingOrder;
import com.agrestina.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pending_ordered_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PendingOrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pending_order_id", nullable = false)
    private PendingOrder pendingOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    public double getTotal() {
        return unitPrice.doubleValue() * quantity;
    }

    public PendingOrderedItem(PendingOrder pendingOrder, Product product, Integer quantity) {
        this.pendingOrder = pendingOrder;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    @Override
    public String toString() {
        return "Pending Ordered Item: " + product +
                " Quantity: " + quantity;
    }
}