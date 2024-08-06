package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderCriteria {

    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingCost;
    private BigDecimal discountAmount;

    public OrderCriteria(BigDecimal totalAmount, BigDecimal taxAmount, BigDecimal shippingCost, BigDecimal discountAmount) {
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.shippingCost = shippingCost;
        this.discountAmount = discountAmount;
    }
}
