package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.PermissionsService;

import java.util.Optional;

public interface RegistrationStrategy
{
    String getRole();
    Optional<?> register(Registration registrationDTO, PermissionsService permissionsService);
}
