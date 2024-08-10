package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService
{
    private UserService userService;

    @Autowired
    public RegistrationService(UserService userService)
    {
        this.userService = userService;
    }

    public void registerUser(RegistrationDTO registrationDTO)
    {
        userService.createUserFromRegistration(registrationDTO);
    }

}
