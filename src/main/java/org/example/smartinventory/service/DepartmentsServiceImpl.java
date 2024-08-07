package org.example.smartinventory.service;

import org.example.smartinventory.entities.DepartmentEntity;
import org.example.smartinventory.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentsServiceImpl implements DepartmentsService
{
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentsServiceImpl(DepartmentRepository departmentRepository)
    {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Collection<DepartmentEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(DepartmentEntity departmentEntity) {

    }

    @Override
    public void delete(DepartmentEntity departmentEntity) {

    }

    @Override
    public Optional<DepartmentEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<DepartmentEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
