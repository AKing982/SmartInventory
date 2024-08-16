package org.example.smartinventory.service;

import org.example.smartinventory.entities.RoleEntity;

import java.util.Optional;
import java.util.Set;

public interface RoleService extends ServiceModel<RoleEntity>
{
    Set<String> getUserRoles(Long userId);
    Set<String> getUserRolePermissions(Long userId);
    void addRoleToUser(Long userId, Integer roleId);
    void removeRoleFromUser(Long userId, Integer roleId);
    Optional<RoleEntity> findByName(String name);
}
