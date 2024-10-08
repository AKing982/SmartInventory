package org.example.smartinventory.repository;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{
    @Query("UPDATE ProductEntity p SET p.quantity =:quantity WHERE p.id =:id")
    Optional<ProductEntity> updateProductQuantity(@Param("id") int id, @Param("quantity") Integer quantity);

    @Query("UPDATE ProductEntity p SET p.price =:price WHERE p.id =:id")
    Optional<ProductEntity> updateProductPrice(@Param("id") Long id, @Param("price") BigDecimal price);

    @Query("UPDATE ProductEntity p SET p.category =:category WHERE p.id =:id")
    Optional<ProductEntity> updateProductCategory(@Param("id") Long id, @Param("category") CategoryEntity category);

    @Query("UPDATE ProductEntity p SET p=:new WHERE p.id =:id")
    Optional<ProductEntity> updateProduct(@Param("id") int id, @Param("new") ProductEntity newProduct);
}
