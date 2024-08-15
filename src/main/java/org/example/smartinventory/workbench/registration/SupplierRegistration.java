package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SupplierRegistration extends AbstractRegistrationBase<SupplierEntity> implements RegistrationStrategy<SupplierEntity>
{
    private SupplierService supplierService;

    @Autowired
    public SupplierRegistration(SupplierService supplierService)
    {
        this.supplierService = supplierService;
    }

    @Override
    public final String getRole() {
        return "ROLE_SUPPLIER";
    }

    @Override
    public Optional<SupplierEntity> register(Registration registration, PermissionsService permissionsService)
    {
        if(registration == null || permissionsService == null){
            throw new IllegalArgumentException("registration and permissionsService can't be null");
        }

        SupplierEntity supplier = createSupplier(registration);
        Set<Permission> permissions = getPermissions(supplier, permissionsService);
        addRoleToPermissions(permissions, getRole(), permissionsService);
        setRoleConfigurationForEntity(permissions, supplier);
        saveSupplier(supplier);
        return Optional.of(supplier);
    }

    @Override
    public void setRoleConfigurationForEntity(Set<Permission> permissions, SupplierEntity supplier){
        RoleEntity createSupplierRole = createRoleEntity(getRole(), permissions);
        Set<RoleEntity> roles = Set.of(createSupplierRole);
        UserEntity user = supplier.getUser();
        user.setRoles(roles);
    }

    private void saveSupplier(SupplierEntity supplier){
        supplierService.save(supplier);
    }


    private SupplierEntity createSupplier(Registration registration){
        return supplierService.createSupplierFromRegistration(registration);
    }

    @Override
    protected Set<Permission> getPermissions(SupplierEntity supplier, PermissionsService permissionsService){
        if(supplier == null || permissionsService == null){
            throw new IllegalArgumentException("supplier can't be null");
        }
        UserEntity user = supplier.getUser();
        validateUser(user);
        return permissionsService.getPermissionsForUser(user);
    }
}
