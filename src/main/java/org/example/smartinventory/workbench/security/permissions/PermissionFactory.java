package org.example.smartinventory.workbench.security.permissions;

import org.example.smartinventory.model.Permission;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class PermissionFactory {
    private final Map<String, PermissionCreator> creators = new HashMap<>();

    public PermissionFactory(){
        creators.put("USER", new RegularUserPermissions());
        creators.put("MANAGER", new ManagerPermissions());
        creators.put("SUPPLIER", new SupplierPermissions());
        creators.put("EMPLOYEE", new EmployeePermissions());
    }

    public Set<Permission> createPermissions(String role)
    {
        PermissionCreator creator = creators.get(role);
        if(creator == null){
            throw new IllegalArgumentException("Invalid Role: " + role);
        }
        return creator.createPermissions();
    }

    public void registerCreator(String role, PermissionCreator creator){
        creators.put(role, creator);
    }
}
