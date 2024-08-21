package org.example.smartinventory.repository;

import org.example.smartinventory.entities.SkuHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Repository
public interface SkuHistoryRepository extends JpaRepository<SkuHistoryEntity, Long>
{

    @Query("SELECT s FROM SkuHistoryEntity s WHERE s.categoryCode =:cat AND s.supplierCode =:sup")
    Optional<SkuHistoryEntity> findByCategoryCodeAndSupplierCode(@Param("cat") String cat, @Param("sup") String sup);

}
