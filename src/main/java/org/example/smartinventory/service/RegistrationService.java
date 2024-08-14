package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.*;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.workbench.registration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService
{
    private RegistrationFactory registrationFactory;
    private PermissionsService permissionsService;
    private RoleService roleService;

    @Autowired
    public RegistrationService(RegistrationFactory registrationFactory,
                               PermissionsService permissionsService,
                               RoleService roleService) {
        this.registrationFactory = registrationFactory;
        this.permissionsService = permissionsService;
        this.roleService = roleService;
    }

    public Optional<UserEntity> createDefaultUser(Registration registration){
        if(registration == null){
            throw new IllegalArgumentException("registration is null");
        }
        return null;
    }

    public Optional<?> register(String role, Registration registration)
    {
        RegistrationStrategy registrationStrategy = registrationFactory.getStrategy(role);
        return registrationStrategy.register(registration, permissionsService);
    }
}
