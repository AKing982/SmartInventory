package org.example.smartinventory.repository;

import org.example.smartinventory.entities.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Integer>
{

}
