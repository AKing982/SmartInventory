package org.example.smartinventory.repository;

import org.example.smartinventory.entities.EmployeeRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRolesRepository extends JpaRepository<EmployeeRolesEntity, Long>
{

}
