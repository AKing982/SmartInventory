package org.example.smartinventory.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum EmployeeRole
{
    INVENTORY_MANAGER(Permission.VIEW_INVENTORY, Permission.UPDATE_INVENTORY, Permission.GENERATE_REPORTS),
    WAREHOUSE_STAFF(Permission.VIEW_INVENTORY, Permission.UPDATE_INVENTORY),
    SALES_REPRESENTATIVE(Permission.VIEW_INVENTORY, Permission.RESERVE_ITEMS),
    PROCUREMENT_MANAGER(Permission.VIEW_INVENTORY, Permission.CREATE_PURCHASE_ORDERS, Permission.MANAGE_SUPPLIERS),
    FINANCE_TEAM(Permission.VIEW_INVENTORY, Permission.VIEW_FINANCIALS, Permission.GENERATE_REPORTS),
    IT_ADMIN(Permission.MANAGE_USERS, Permission.CONFIGURE_SYSTEM),
    EXECUTIVE_MANAGEMENT(Permission.VIEW_INVENTORY, Permission.VIEW_FINANCIALS, Permission.VIEW_ANALYTICS),
    SUPPLIER(Permission.VIEW_OWN_INVENTORY);

    private final Set<Permission> permissions;

    EmployeeRole(Permission... permissions) {
        this.permissions = new HashSet<>(Arrays.asList(permissions));
    }

    public Set<Permission> getPermissions() {
        return new HashSet<>(permissions);
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }
}
