package org.example.smartinventory.service;

import org.example.smartinventory.entities.SkuHistoryEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.repository.SkuHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SkuHistoryServiceImpl implements SkuHistoryService
{
    private SkuHistoryRepository skuHistoryRepository;

    @Autowired
    public SkuHistoryServiceImpl(SkuHistoryRepository skuHistoryRepository){
        this.skuHistoryRepository = skuHistoryRepository;
    }

    @Override
    public Collection<SkuHistoryEntity> findAll() {
        return skuHistoryRepository.findAll();
    }

    @Override
    public void save(SkuHistoryEntity skuHistoryEntity) {
        skuHistoryRepository.save(skuHistoryEntity);
    }

    @Override
    public void delete(SkuHistoryEntity skuHistoryEntity) {
        skuHistoryRepository.delete(skuHistoryEntity);
    }

    @Override
    public Optional<SkuHistoryEntity> findById(Long id) {
        return skuHistoryRepository.findById(id);
    }

    @Override
    public Collection<SkuHistoryEntity> findAllById(Iterable<Long> ids) {
        return skuHistoryRepository.findAllById(ids);
    }

    @Override
    public SkuHistoryEntity createSkuHistory(String categoryCode, String supplierCode) {
        SkuHistoryEntity skuHistoryEntity = new SkuHistoryEntity();
        skuHistoryEntity.setCategoryCode(categoryCode);
        skuHistoryEntity.setSupplierCode(supplierCode);
        return skuHistoryRepository.save(skuHistoryEntity);
    }
}
