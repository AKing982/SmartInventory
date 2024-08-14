package org.example.smartinventory.workbench.security.permissions;

import org.example.smartinventory.model.Permission;

import java.util.HashSet;
import java.util.Set;

public class EmployeePermissions implements PermissionCreator
{
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public Set<Permission> createPermissions() {
        permissions.add(Permission.VIEW_INVENTORY);
        permissions.add(Permission.VIEW_ORDER_HISTORY);
        permissions.add(Permission.UPDATE_USER_PROFILE);
        permissions.add(Permission.GENERATE_REPORTS);
        permissions.add(Permission.PROCESS_ORDERS);
        permissions.add(Permission.UPDATE_INVENTORY);
        return permissions;
    }
}
