package org.example.smartinventory.service;

import org.example.smartinventory.entities.StockEntity;

import java.util.Collection;
import java.util.Optional;

public interface StockService extends ServiceModel<StockEntity>
{
    Collection<StockEntity> getStocksByProductId(int productId);
    Optional<StockEntity> getLowStockItems();

    StockEntity updateStock(StockEntity stock);
}
