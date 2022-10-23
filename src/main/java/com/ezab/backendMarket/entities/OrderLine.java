package com.ezab.backendMarket.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ORDER_LINES")
public class OrderLine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_order", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "fk_product", nullable = false)
    private Product product;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "total", nullable = false)
    private Double total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine)) return false;
        OrderLine orderLine = (OrderLine) o;
        return id.equals(orderLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
