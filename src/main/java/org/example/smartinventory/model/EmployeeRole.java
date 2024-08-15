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
    ROLE_EMPLOYEE(Permission.VIEW_INVENTORY, Permission.VIEW_ORDER_HISTORY, Permission.UPDATE_USER_PROFILE, Permission.GENERATE_REPORTS, Permission.PROCESS_ORDERS, Permission.UPDATE_INVENTORY),
    ROLE_SUPPLIER(Permission.VIEW_PRODUCT_CATALOG, Permission.CHECK_ITEM_AVAILABILITY, Permission.UPDATE_PRODUCT_INFORMATION, Permission.MANAGE_OWN_INVENTORY, Permission.VIEW_PURCHASE_ORDERS),
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
