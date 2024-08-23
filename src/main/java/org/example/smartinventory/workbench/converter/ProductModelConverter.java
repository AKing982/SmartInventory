package org.example.smartinventory.workbench.converter;

import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductModelConverter implements ModelToEntityConverter<Product, ProductEntity>
{
    @Override
    public ProductEntity convert(Product source) {
        ProductEntity entity = new ProductEntity();
        entity.setNotes(source.getNotes().toString());
        entity.setExpirationDate(source.getExpirationDate());
        entity.setProductBrand(source.getProductBrand());
        entity.setSupplier(source.getSupplier());
        entity.setReorderPoint(source.getReorderPoint());
        entity.setMarkupPercentage(source.getMarkupPercentage());
        entity.setCostPrice(source.getCostPrice());
        entity.setCategory(source.getProductCategory());
        entity.setPrice(source.getProductPrice());
        entity.setDescription(source.getProductDescription());
        entity.setSku(source.getProductSKU());
        entity.setQuantity(source.getProductQuantity());
        entity.setModelNumber(source.getModelNumber());
        return entity;
    }

    public Set<ProductEntity> convert(List<Product> source) {
        Set<ProductEntity> result = new HashSet<>();
        for(Product product : source) {
            result.add(convert(product));
        }
        return result;
    }
}
