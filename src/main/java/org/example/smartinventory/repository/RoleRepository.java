package org.example.smartinventory.repository;

import org.example.smartinventory.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>
{
    @Query("SELECT r FROM RoleEntity r WHERE r.role =:name")
    Optional<RoleEntity> findByName(@Param("name") String name);
}
