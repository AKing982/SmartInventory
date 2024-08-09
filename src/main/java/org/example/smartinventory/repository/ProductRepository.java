package org.example.smartinventory.repository;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{
    @Query("UPDATE ProductEntity p SET p.productQuantity =:quantity WHERE p.productId =:id")
    int updateProductQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    @Query("UPDATE ProductEntity p SET p.productPrice =:price WHERE p.productId =:id")
    int updateProductPrice(@Param("id") Long id, @Param("price") BigDecimal price);

    @Query("UPDATE ProductEntity p SET p.productCategory =:category WHERE p.productId =:id")
    int updateProductCategory(@Param("id") Long id, @Param("category") CategoryEntity category);
}
