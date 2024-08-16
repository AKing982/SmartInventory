package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.*;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.workbench.registration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService
{
    private RegistrationFactory registrationFactory;
    private PermissionsService permissionsService;
    private UserService userService;
    private Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    public RegistrationService(RegistrationFactory registrationFactory,
                               PermissionsService permissionsService,
                               UserService userService) {
        this.registrationFactory = registrationFactory;
        this.permissionsService = permissionsService;
        this.userService = userService;
    }

    public Optional<UserEntity> createDefaultUser(Registration registration){
        if(registration == null){
            throw new IllegalArgumentException("registration is null");
        }
        validateRegistrationParameters(registration);
        UserEntity user = createDefaultUserFromRegistration(registration);
        return Optional.of(user);
    }

    private UserEntity createDefaultUserFromRegistration(Registration registration){
        return userService.createUserFromRegistration(registration);
    }

    public void validateRegistrationParameters(Registration registration)
    {
        if(registration.getEmail() == null || registration.getRole() == null ||
           registration.getCompany() == null || registration.getPassword() == null ||
           registration.getFirstName() == null || registration.getLastName() == null ||
           registration.getUsername() == null || registration.getJobTitle() == null){
            throw new IllegalArgumentException("Registration has null parameters: " + registration);
        }
    }

    public <T> Optional<T> register(String role, Registration registration)
    {
//        validateRoleIsEmpty(role);
        validateRegistration(registration);
        LOGGER.info("Role: {}", role);
        if(!role.contains("ROLE_")){
            throw new IllegalArgumentException("Registration has no valid role: " + registration);
        }
        RegistrationStrategy<T> registrationStrategy = registrationFactory.getStrategy(role);
        return registrationStrategy.register(registration, permissionsService);
    }

    public void validateRegistration(Registration registration){
        if(registration == null){
            throw new IllegalArgumentException("Unable to complete registration from null registration");
        }
    }

    public void validateRoleIsEmpty(String role){
        if(role.isEmpty()){
            throw new IllegalArgumentException("Role is Empty");
        }
    }
}
