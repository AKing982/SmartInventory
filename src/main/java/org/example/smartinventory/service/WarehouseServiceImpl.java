package org.example.smartinventory.service;

import org.example.smartinventory.entities.WarehouseEntity;
import org.example.smartinventory.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService
{
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository)
    {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Collection<WarehouseEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(WarehouseEntity warehouseEntity) {

    }

    @Override
    public void delete(WarehouseEntity warehouseEntity) {

    }

    @Override
    public Optional<WarehouseEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<WarehouseEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
