package org.example.smartinventory.service;

import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    @Override
    public Collection<OrderEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(OrderEntity orderEntity) {

    }

    @Override
    public void delete(OrderEntity orderEntity) {

    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<OrderEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
