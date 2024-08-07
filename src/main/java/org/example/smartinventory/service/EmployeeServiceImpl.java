package org.example.smartinventory.service;

import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Collection<EmployeeEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(EmployeeEntity employeeEntity) {

    }

    @Override
    public void delete(EmployeeEntity employeeEntity) {

    }

    @Override
    public Optional<EmployeeEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<EmployeeEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
