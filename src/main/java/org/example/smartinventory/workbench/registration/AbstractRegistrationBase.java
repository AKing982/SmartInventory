package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.service.PermissionsService;

import java.util.Set;

public abstract class AbstractRegistrationBase<T>
{

    protected abstract Set<Permission> getPermissions(T entity, PermissionsService permissionsService);

    protected void validateUser(UserEntity user){
        if(user == null){
            throw new IllegalArgumentException("user can't be null");
        }
    }

    protected void addRoleToPermissions(Set<Permission> permissions, String role, PermissionsService permissionService){
        permissionService.addRole(role, permissions);
    }

    protected abstract void setRoleConfigurationForEntity(Set<Permission> permissions, T entity);

    protected RoleEntity createRoleEntity(String role, Set<Permission> permissions){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        roleEntity.setPermissions(permissions);
        return roleEntity;
    }
}
