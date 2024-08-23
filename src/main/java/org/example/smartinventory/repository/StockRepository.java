package org.example.smartinventory.repository;

import org.example.smartinventory.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long>
{
    @Query("UPDATE StockEntity s SET s =:s")
    StockEntity updateStock(@Param("s") StockEntity s);

}
