package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService extends ServiceModel<ProductEntity>
{
    // Added methods
    List<ProductEntity> getAllProducts();
    boolean validateProduct(ProductEntity product);

    // Modified methods
    void deleteProduct(Long productId);
    Optional<ProductEntity> updateProductPrice(Long productId, BigDecimal newPrice);
    Optional<ProductEntity> updateProductQuantity(int productId, int newQuantity);
    Optional<ProductEntity> updateProductCategory(Long productId, CategoryEntity newCategory);
}
