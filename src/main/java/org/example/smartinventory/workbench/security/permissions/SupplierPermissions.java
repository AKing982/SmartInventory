package org.example.smartinventory.workbench.security.permissions;

import org.example.smartinventory.model.Permission;

import java.util.HashSet;
import java.util.Set;

public class SupplierPermissions implements PermissionCreator
{
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public Set<Permission> createPermissions() {
        permissions.add(Permission.VIEW_PRODUCT_CATALOG);
        permissions.add(Permission.CHECK_ITEM_AVAILABILITY);
        permissions.add(Permission.UPDATE_PRODUCT_INFORMATION);
        permissions.add(Permission.MANAGE_OWN_INVENTORY);
        permissions.add(Permission.VIEW_PURCHASE_ORDERS);
        return permissions;
    }
}
