package org.example.smartinventory.workbench.security.permissions;

import org.example.smartinventory.model.Permission;

import java.util.HashSet;
import java.util.Set;

public class ManagerPermissions implements PermissionCreator
{
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public Set<Permission> createPermissions() {
        permissions.add(Permission.MANAGE_INVENTORY_THRESHOLDS);
        permissions.add(Permission.MANAGE_USERS);
        permissions.add(Permission.APPROVE_INVENTORY);
        permissions.add(Permission.GENERATE_ADVANCED_REPORTS);
        permissions.add(Permission.MANAGE_STAFF_SCHEDULES);
        permissions.add(Permission.MANAGE_SUPPLIERS);
        return permissions;
    }
}
