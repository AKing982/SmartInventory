package org.example.smartinventory.service;

import org.example.smartinventory.entities.SkuHistoryEntity;
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
    public Optional<Integer> findLastSkuSequenceByKey(String key) {
        // Split the key
        String[] split = key.split("-");
        String categoryCode = split[0];
        String supplierCode = split[1];

        Optional<SkuHistoryEntity> skuHistoryEntity = skuHistoryRepository.findByCategoryCodeAndSupplierCode(categoryCode, supplierCode);
        if(skuHistoryEntity.isEmpty()){
            throw new NoSuchElementException();
        }
        Integer sequence = skuHistoryEntity.get().getSequence();
        return Optional.of(sequence);
    }
}
