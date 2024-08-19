import {RoleType} from "./items/Items";

export enum Permissions {
    VIEW_ORDER_HISTORY,
    UPDATE_USER_PROFILE,
    PLACE_ORDERS,
    VIEW_PRODUCT_CATALOG,
    CHECK_ITEM_AVAILABILITY,
    UPDATE_PRODUCT_INFORMATION,
    MANAGE_OWN_INVENTORY,
    VIEW_PURCHASE_ORDERS,
    VIEW_INVENTORY,
    GENERATE_REPORTS,
    UPDATE_INVENTORY,
    PROCESS_ORDERS
}

interface PermissionModel {
    authority: RoleType,
    permission: Permissions[]
}

export const permissionItems: PermissionModel[] = [
    {authority: RoleType.ROLE_USER,
        permission: [Permissions.CHECK_ITEM_AVAILABILITY,
            Permissions.PLACE_ORDERS, Permissions.VIEW_ORDER_HISTORY,
            Permissions.UPDATE_USER_PROFILE, Permissions.VIEW_PRODUCT_CATALOG] },
    {authority: RoleType.ROLE_SUPPLIER,
        permission: [Permissions.VIEW_PRODUCT_CATALOG,
        Permissions.CHECK_ITEM_AVAILABILITY,
            Permissions.UPDATE_PRODUCT_INFORMATION,
        Permissions.VIEW_PRODUCT_CATALOG, Permissions.MANAGE_OWN_INVENTORY, Permissions.VIEW_PURCHASE_ORDERS]},
    {authority: RoleType.ROLE_EMPLOYEE,
        permission: [Permissions.VIEW_INVENTORY,
            Permissions.VIEW_ORDER_HISTORY,
            Permissions.UPDATE_USER_PROFILE,
            Permissions.GENERATE_REPORTS, Permissions.PROCESS_ORDERS, Permissions.UPDATE_INVENTORY]}
]

export const hasPermission = (role: RoleType, requiredPermission: Permissions): boolean => {
    const rolePermissions = permissionItems.find(item => item.authority === role);
    return rolePermissions ? rolePermissions.permission.includes(requiredPermission) : false;
};

export const getRolePermissions = (role: RoleType): Permissions[] => {
    const rolePermissions = permissionItems.find(item => item.authority === role);
    return rolePermissions ? rolePermissions.permission : [];
};

