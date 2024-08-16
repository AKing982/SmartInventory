package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.RoleService;
import org.example.smartinventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public abstract class AbstractRegistrationBase<T>
{
    protected RoleService roleService;
    protected UserService userService;

    @Autowired
    public AbstractRegistrationBase(RoleService roleService,
                                    UserService userService)
    {
        this.roleService = roleService;
        this.userService = userService;
    }

    protected abstract Set<Permission> getPermissions(T entity, PermissionsService permissionsService);

    protected void validateUser(UserEntity user){
        if(user == null){
            throw new IllegalArgumentException("user can't be null");
        }
    }

    protected UserEntity createDefaultUser(Registration registration){
        return userService.createUserFromRegistration(registration);
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
