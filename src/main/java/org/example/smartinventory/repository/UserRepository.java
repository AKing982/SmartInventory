package org.example.smartinventory.repository;

import org.example.smartinventory.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    @Query("SELECT u FROM UserEntity u WHERE u.username =:user")
    Optional<UserEntity> findByUsername(@Param("user") String username);

    @Query("SELECT u FROM UserEntity u WHERE u.email LIKE :email")
    Optional<UserEntity> findByEmail(String email);
}
