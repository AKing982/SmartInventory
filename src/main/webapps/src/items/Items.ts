interface RoleModel {
    role: RoleType;
    item: RoleItem;
}

export interface RoleItem {
    roleName: string;
}

export enum RoleType {
    ROLE_SUPPLIER = "ROLE_SUPPLIER",
    ROLE_USER = "ROLE_USER",
    ROLE_EMPLOYEE = "ROLE_EMPLOYEE",
    ROLE_MANAGER = "ROLE_MANAGER",
    ROLE_ADMIN = "ROLE_ADMIN"
}

export const roleItems : RoleModel[] = [
    { role: RoleType.ROLE_SUPPLIER, item: { roleName: 'Inventory Supplier' } },
    { role: RoleType.ROLE_ADMIN, item: { roleName: 'Administrator' } },
    { role: RoleType.ROLE_USER, item: { roleName: 'Regular User' } },
    { role: RoleType.ROLE_EMPLOYEE, item: { roleName: 'Inventory Specialist' } },
    { role: RoleType.ROLE_MANAGER, item: { roleName: 'Inventory Manager' } }
]

