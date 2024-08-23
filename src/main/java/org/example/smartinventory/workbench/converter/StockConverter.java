package org.example.smartinventory.workbench.converter;

import org.example.smartinventory.controllers.StockController;
import org.example.smartinventory.dto.StockDTO;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.entities.StockEntity;
import org.example.smartinventory.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;



public class StockConverter implements ModelToEntityConverter<StockDTO, StockEntity>
{
    private ProductModelConverter productModelConverter;
    private Logger LOGGER = LoggerFactory.getLogger(StockConverter.class);

    public StockConverter()
    {
        this.productModelConverter = new ProductModelConverter();
    }

    @Override
    public StockEntity convert(StockDTO source) {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setId(source.id());
        stockEntity.setProducts(convertStockToList(source));
        stockEntity.setIsLow(source.isLow());
        stockEntity.setQuantity(source.quantity());
        stockEntity.setLastUpdated(LocalDateTime.now());
        LOGGER.info("Stock: {}", stockEntity);
        return stockEntity;
    }

    private Set<ProductEntity> convertStockToList(StockDTO stockDTO) {
        List<Product> products = stockDTO.products();
        return productModelConverter.convert(products);
    }
}
