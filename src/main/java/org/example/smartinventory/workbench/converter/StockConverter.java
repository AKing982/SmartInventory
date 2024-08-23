package org.example.smartinventory.workbench.converter;

import org.example.smartinventory.controllers.StockController;
import org.example.smartinventory.dto.StockDTO;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.entities.StockEntity;
import org.example.smartinventory.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class StockConverter implements ModelToEntityConverter<StockDTO, StockEntity>
{
    private ProductModelConverter productModelConverter;

    @Autowired
    public StockConverter(ProductModelConverter productModelConverter)
    {
        this.productModelConverter = productModelConverter;
    }

    @Override
    public StockEntity convert(StockDTO source) {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setId(source.id());
        stockEntity.setProducts(convertStockToList(source));
        stockEntity.setQuantity(source.quantity());
        return stockEntity;
    }

    private Set<ProductEntity> convertStockToList(StockDTO stockDTO) {
        List<Product> products = stockDTO.products();
        return productModelConverter.convert(products);
    }
}
