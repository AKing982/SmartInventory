package org.example.smartinventory.repository;

import org.example.smartinventory.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long>
{

}
