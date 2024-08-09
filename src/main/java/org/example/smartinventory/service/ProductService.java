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
    Optional<ProductEntity> assignCategory(Long productId, String category);
    boolean validateProduct(ProductEntity product);

    // Modified methods
    void deleteProduct(Long productId);
    void deleteProducts(List<Long> productIds);
    int updateProductPrice(Long productId, BigDecimal newPrice);
    int updateProductQuantity(Long productId, int newQuantity);
    int updateProductCategory(Long productId, CategoryEntity newCategory);
}
