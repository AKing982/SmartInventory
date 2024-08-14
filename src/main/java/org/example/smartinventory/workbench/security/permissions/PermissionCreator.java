package org.example.smartinventory.workbench.security.permissions;

import org.example.smartinventory.model.Permission;

import java.util.Set;

public interface PermissionCreator
{
    Set<Permission> createPermissions();
}
