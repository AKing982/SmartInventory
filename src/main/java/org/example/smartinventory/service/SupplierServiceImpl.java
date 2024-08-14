package org.example.smartinventory.service;

import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService
{
    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository)
    {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Collection<SupplierEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(SupplierEntity supplierEntity) {

    }

    @Override
    public void delete(SupplierEntity supplierEntity) {

    }

    @Override
    public Optional<SupplierEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<SupplierEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
