package org.example.smartinventory.repository;

import org.example.smartinventory.entities.InventoryNotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryNotesRepository extends JpaRepository<InventoryNotesEntity, Long>
{

}
