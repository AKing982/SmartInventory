package org.example.smartinventory.workbench.security.permissions;

import org.example.smartinventory.model.Permission;

import java.util.HashSet;
import java.util.Set;

public class RegularUserPermissions implements PermissionCreator
{
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public Set<Permission> createPermissions() {
        permissions.add(Permission.VIEW_PRODUCT_CATALOG);
        permissions.add(Permission.CHECK_ITEM_AVAILABILITY);
        permissions.add(Permission.PLACE_ORDERS);
        permissions.add(Permission.VIEW_ORDER_HISTORY);
        permissions.add(Permission.UPDATE_USER_PROFILE);
        return permissions;
    }
}
