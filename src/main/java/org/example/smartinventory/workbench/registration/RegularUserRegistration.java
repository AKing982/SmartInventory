package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegularUserRegistration implements RegistrationStrategy
{
    private UserService userService;

    @Autowired
    public RegularUserRegistration(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String getRole() {
        return "ROLE_USER";
    }

    @Override
    public Optional<? extends UserEntity> register(Registration registration, PermissionsService permissionsService) {
        return null;
    }
}
