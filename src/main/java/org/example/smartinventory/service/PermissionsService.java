package org.example.smartinventory.service;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Role;
import org.example.smartinventory.workbench.security.permissions.PermissionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionsService
{
    private Map<String, Set<Permission>> permissions = new HashMap<>();
    private final PermissionFactory permissionFactory;

    @Autowired
    public PermissionsService(PermissionFactory permissionFactory)
    {
        this.permissionFactory = permissionFactory;
        initializeRolePermissions();
    }

    private void initializeRolePermissions(){
        for(String role : Arrays.asList("USER", "MANAGER", "SUPPLIER", "EMPLOYEE")){
            permissions.put(role, permissionFactory.createPermissions(role));
        }
    }

    public Set<Permission> getPermissionsForUser(UserEntity user){
        Set<Permission> userPermissions = new HashSet<>();
        for(RoleEntity role : user.getRoles())
        {
            userPermissions.addAll(permissions.getOrDefault(role.getRole(), new HashSet<>()));
        }
        return userPermissions;
    }

    public boolean hasPermission(UserEntity user, Permission permission){
        return getPermissionsForUser(user).contains(permission);
    }

    public void addRole(String role, Set<Permission> newPermissions){
        permissions.put(role, newPermissions);
    }
}

