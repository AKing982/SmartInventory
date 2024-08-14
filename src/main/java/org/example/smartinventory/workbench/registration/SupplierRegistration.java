package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SupplierRegistration implements RegistrationStrategy
{
    private SupplierService supplierService;

    @Autowired
    public SupplierRegistration(SupplierService supplierService)
    {
        this.supplierService = supplierService;
    }

    @Override
    public String getRole() {
        return "ROLE_SUPPLIER";
    }

    @Override
    public Optional<? extends UserEntity> register(Registration registration, PermissionsService permissionsService)
    {
        return Optional.empty();
    }
}
