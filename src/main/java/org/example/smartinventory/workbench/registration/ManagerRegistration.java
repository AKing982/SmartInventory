package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.ManagerService;
import org.example.smartinventory.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ManagerRegistration implements RegistrationStrategy
{
    private ManagerService managerService;

    @Autowired
    public ManagerRegistration(ManagerService managerService){
        this.managerService = managerService;
    }

    @Override
    public String getRole() {
        return "ROLE_MANAGER";
    }

    @Override
    public Optional<?> register(Registration registration, PermissionsService permissionsService) {
        return Optional.empty();
    }
}
