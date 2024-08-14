package org.example.smartinventory.workbench.converter;

import lombok.NoArgsConstructor;
import org.example.smartinventory.dto.ProductDTO;
import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@NoArgsConstructor
public class ProductDTOConverter implements ModelToEntityConverter<ProductDTO, ProductEntity>
{
    private Logger LOGGER = LoggerFactory.getLogger(ProductDTOConverter.class);

    @Override
    public ProductEntity convert(ProductDTO source) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setSku(source.productSKU());
        productEntity.setName(source.productName());
        productEntity.setDescription(source.productDescription());
        productEntity.setPrice(source.productPrice());
        productEntity.setQuantity(source.productQuantity());
        productEntity.setCategory(source.productCategory());
        productEntity.setDateAdded(LocalDate.now());
        LOGGER.info("ProductEntity: {}", productEntity);
        return productEntity;
    }


}
