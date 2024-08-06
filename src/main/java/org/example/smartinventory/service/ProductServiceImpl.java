package org.example.smartinventory.service;

import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @Override
    public Collection<ProductEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(ProductEntity productEntity) {

    }

    @Override
    public void delete(ProductEntity productEntity) {

    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<ProductEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
