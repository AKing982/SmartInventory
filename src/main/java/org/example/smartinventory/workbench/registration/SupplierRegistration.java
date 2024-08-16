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
import org.example.smartinventory.service.RoleService;
import org.example.smartinventory.service.SupplierService;
import org.example.smartinventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SupplierRegistration extends AbstractRegistrationBase<SupplierEntity> implements RegistrationStrategy<SupplierEntity>
{
    private SupplierService supplierService;
    private UserService userService;

    @Autowired
    public SupplierRegistration(SupplierService supplierService,
                                UserService userService,
                                RoleService roleService)
    {
        super(roleService, userService);
        this.supplierService = supplierService;
        this.userService = userService;
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

        UserEntity user = createDefaultUser(registration);
        SupplierEntity supplier = createSupplier(registration, user);
        Set<Permission> permissions = getPermissions(supplier, permissionsService);
        addRoleToPermissions(permissions, getRole(), permissionsService);
        setRoleConfigurationForEntity(permissions, supplier);
        saveSupplier(supplier);
        return Optional.of(supplier);
    }

    @Override
    public void setRoleConfigurationForEntity(Set<Permission> permissions, SupplierEntity supplier){
        RoleEntity supplierRole = roleService.findByName(getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        UserEntity user = supplier.getUser();
        roleService.addRoleToUser(user.getId(), supplierRole.getId());

        userService.save(user);
        supplier.setUser(user);
    }

    private void saveSupplier(SupplierEntity supplier){
        supplierService.save(supplier);
    }


    private SupplierEntity createSupplier(Registration registration, UserEntity user){
        return supplierService.createSupplierFromRegistration(registration, user);
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
