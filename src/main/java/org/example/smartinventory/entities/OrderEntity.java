package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartinventory.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private UserEntity createdByUser;

    private String orderNumber;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customerId")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notesId")
    private InventoryNotesEntity inventoryNotes;

    private BigDecimal totalAmount;

    private String billingAddress;
    private String shippingAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderEntity(int orderId, UserEntity createdByUser, String orderNumber, LocalDateTime orderDate, OrderStatus orderStatus, CustomerEntity customer, InventoryNotesEntity inventoryNotes, BigDecimal totalAmount, String billingAddress, String shippingAddress, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.createdByUser = createdByUser;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.inventoryNotes = inventoryNotes;
        this.totalAmount = totalAmount;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderEntity(int orderId, LocalDateTime createdAt, BigDecimal totalAmount, CustomerEntity customer) {
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.customer = customer;
    }

    public OrderEntity(int orderId, LocalDateTime createdAt, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }
}
