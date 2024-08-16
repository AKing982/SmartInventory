package org.example.smartinventory.workbench.security.permissions;

import jakarta.annotation.PostConstruct;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PermissionCacheService
{
    private final Map<String, Set<Permission>> rolePermissions;

    public PermissionCacheService(){
        this.rolePermissions = new ConcurrentHashMap<>();
    }

    @PostConstruct
    private void initializePermissionCache() {
        // Initialize permissions for each role
        rolePermissions.put("ROLE_SUPPLIER", new HashSet<>(Arrays.asList(
                Permission.VIEW_PRODUCT_CATALOG,
                Permission.CHECK_ITEM_AVAILABILITY,
                Permission.UPDATE_PRODUCT_INFORMATION,
                Permission.MANAGE_OWN_INVENTORY,
                Permission.VIEW_PURCHASE_ORDERS
        )));

        rolePermissions.put("ROLE_USER", new HashSet<>(Arrays.asList(
                Permission.VIEW_PRODUCT_CATALOG,
                Permission.CHECK_ITEM_AVAILABILITY,
                Permission.PLACE_ORDERS,
                Permission.VIEW_ORDER_HISTORY,
                Permission.UPDATE_USER_PROFILE
        )));

        // Add other roles as needed
    }

    public Set<Permission> getPermissionsForRole(String roleName) {
        return rolePermissions.getOrDefault(roleName, new HashSet<>());
    }
}
