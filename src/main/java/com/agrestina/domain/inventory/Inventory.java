package com.agrestina.domain.inventory;

import com.agrestina.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "inventory")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @OneToOne
    private Product product;

    public Inventory(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public void add(Integer quantity) {
        this.quantity += quantity;
    }
    public void decrease(Integer quantity) {
        this.quantity -= quantity;
    }
}