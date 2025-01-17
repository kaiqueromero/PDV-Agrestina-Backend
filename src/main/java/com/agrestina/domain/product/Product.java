package com.agrestina.domain.product;

import com.agrestina.dto.product.RegisterProductDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "products")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private BigDecimal price;

    private boolean active;

    public void disabled(){
        this.active = false;
    }

    public void activate(){this.active = true;}

    public Product(RegisterProductDTO dto){
        this.name = dto.name();
        this.description = dto.description();
        this.category = Category.valueOf(dto.category().toUpperCase());
        this.price = dto.price();
        this.active = true;
    }

    @Override
    public String toString() {
        return "Produto: \n" +
                "Nome: " + name +
                "Descrição: " + description +
                "Categoria: " + category +
                "Preço: " + price;
    }
}
