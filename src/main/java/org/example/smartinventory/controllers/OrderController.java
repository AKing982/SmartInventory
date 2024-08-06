package org.example.smartinventory.controllers;

import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(value="/api/orders")
public class OrderController
{
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<Collection<OrderEntity>> getAllOrders()
    {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable("id") Long id)
    {
        return null;
    }

    @PostMapping("/")
    public void addOrder(@RequestBody OrderEntity orderEntity){

    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable("id") Long id, @RequestBody OrderEntity orderEntity){

    }
}
