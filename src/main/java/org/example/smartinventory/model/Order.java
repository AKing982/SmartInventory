package org.example.smartinventory.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Order
{
    private int orderId;
    private String orderNumber;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private Customer customer;
    private OrderCriteria orderCriteria;
    private Address shippingAddress;
    private Address billingAddress;
    private ShipmentInfo shipmentInfo;

    public Order(int orderId, String orderNumber, LocalDate orderDate, OrderStatus orderStatus, OrderType orderType, Customer customer, OrderCriteria orderCriteria, Address shippingAddress, Address billingAddress, ShipmentInfo shipmentInfo) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.customer = customer;
        this.orderCriteria = orderCriteria;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.shipmentInfo = shipmentInfo;
    }
}
