package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItem
{
    private int Id;
    private Order order;
    private Product product;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal totalPrice;

    public OrderItem(int id, Order order, Product product, BigDecimal unitPrice, BigDecimal discount, BigDecimal totalPrice) {
        Id = id;
        this.order = order;
        this.product = product;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }
}
