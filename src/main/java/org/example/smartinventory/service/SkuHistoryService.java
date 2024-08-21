package org.example.smartinventory.service;

import org.example.smartinventory.entities.SkuHistoryEntity;

import java.util.Optional;

public interface SkuHistoryService extends ServiceModel<SkuHistoryEntity>
{
    Optional<Integer> findLastSkuSequenceByKey(String key);

}
