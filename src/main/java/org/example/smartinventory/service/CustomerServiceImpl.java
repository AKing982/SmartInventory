package org.example.smartinventory.service;

import org.example.smartinventory.entities.CustomerEntity;
import org.example.smartinventory.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService
{
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<CustomerEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(CustomerEntity customerEntity) {

    }

    @Override
    public void delete(CustomerEntity customerEntity) {

    }

    @Override
    public Optional<CustomerEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<CustomerEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
