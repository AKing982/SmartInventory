package org.example.smartinventory.workbench.security.permissions;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.model.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public class PermissionUtil
{
    public static Set<Permission> extractPermissionFromRoles(Set<RoleEntity> roles){
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());
    }
}
