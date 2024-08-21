package org.example.smartinventory.service;

import org.example.smartinventory.entities.SkuHistoryEntity;
import org.example.smartinventory.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SkuHistoryService extends ServiceModel<SkuHistoryEntity>
{
    SkuHistoryEntity createSkuHistory(String categoryCode, String supplierCode);
}
