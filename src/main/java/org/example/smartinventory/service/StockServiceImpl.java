package org.example.smartinventory.service;

import org.example.smartinventory.entities.StockEntity;
import org.example.smartinventory.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService
{
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Collection<StockEntity> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public void save(StockEntity stockEntity) {
        stockRepository.save(stockEntity);
    }

    @Override
    public void delete(StockEntity stockEntity) {
        stockRepository.delete(stockEntity);
    }

    @Override
    public Optional<StockEntity> findById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public Collection<StockEntity> findAllById(Iterable<Long> ids) {
        return stockRepository.findAllById(ids);
    }

    @Override
    public Collection<StockEntity> getStocksByProductId(int productId) {
        return List.of();
    }

    @Override
    public Optional<StockEntity> getLowStockItems() {
        return Optional.empty();
    }

    @Override
    public StockEntity updateStock(StockEntity stock) {
        return null;
    }
}
