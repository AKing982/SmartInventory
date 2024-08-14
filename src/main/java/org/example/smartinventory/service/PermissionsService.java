package org.example.smartinventory.service;

import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Role;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionsService
{
    private EnumMap<Role, Set<Permission>> permissions = new EnumMap<>(Role.class);

    public PermissionsService()
    {
        permissions = new EnumMap<>(Role.class);

    }

    private void initializeRolePermissions(){
        permissions.put(Role.USER, createRegularUserPermissions());
        permissions.put(Role.MANAGER, createManagerPermissions());
        permissions.put(Role.SUPPLIER, createSupplierPermissions());

    }

    private Set<Permission> createRegularUserPermissions(){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.VIEW_PRODUCT_CATALOG);
        permissions.add(Permission.CHECK_ITEM_AVAILABILITY);
        permissions.add(Permission.PLACE_ORDERS);
        permissions.add(Permission.VIEW_ORDER_HISTORY);
        permissions.add(Permission.UPDATE_USER_PROFILE);
        return permissions;
    }

    private Set<Permission> createManagerPermissions()
    {
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.MANAGE_INVENTORY_THRESHOLDS);
        permissions.add(Permission.MANAGE_USERS);
        permissions.add(Permission.APPROVE_INVENTORY);
        permissions.add(Permission.GENERATE_ADVANCED_REPORTS);
        permissions.add(Permission.MANAGE_STAFF_SCHEDULES);
        permissions.add(Permission.MANAGE_SUPPLIERS);
        return permissions;
    }

    private Set<Permission> createSupplierPermissions()
    {
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.VIEW_PRODUCT_CATALOG);
        permissions.add(Permission.CHECK_ITEM_AVAILABILITY);
        permissions.add(Permission.UPDATE_PRODUCT_INFORMATION);
        permissions.add(Permission.MANAGE_OWN_INVENTORY);
        permissions.add(Permission.VIEW_PURCHASE_ORDERS);
        return permissions;
    }

    public Set<Permission> createEmployeePermissions()
    {
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.VIEW_INVENTORY);
        permissions.add(Permission.VIEW_ORDER_HISTORY);
        permissions.add(Permission.UPDATE_USER_PROFILE);
        permissions.add(Permission.GENERATE_REPORTS);
        permissions.add(Permission.PROCESS_ORDERS);
        permissions.add(Permission.UPDATE_INVENTORY);
        return permissions;
    }

    public Set<Permission> getPermissionsForUser(UserEntity user){
        return permissions.getOrDefault(user.getRoles(), new HashSet<>());
    }

    public boolean hasPermission(UserEntity user, Permission permission){
        return getPermissionsForUser(user).contains(permission);
    }
}
