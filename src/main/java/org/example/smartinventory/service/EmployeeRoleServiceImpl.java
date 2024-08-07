package org.example.smartinventory.service;

import org.example.smartinventory.entities.EmployeeRolesEntity;
import org.example.smartinventory.repository.EmployeeRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRoleServiceImpl implements EmployeeRoleService
{
    private EmployeeRolesRepository employeeRolesRepository;

    @Autowired
    public EmployeeRoleServiceImpl(EmployeeRolesRepository employeeRolesRepository)
    {
        this.employeeRolesRepository = employeeRolesRepository;
    }

    @Override
    public Collection<EmployeeRolesEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(EmployeeRolesEntity employeeRolesEntity) {

    }

    @Override
    public void delete(EmployeeRolesEntity employeeRolesEntity) {

    }

    @Override
    public Optional<EmployeeRolesEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<EmployeeRolesEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
