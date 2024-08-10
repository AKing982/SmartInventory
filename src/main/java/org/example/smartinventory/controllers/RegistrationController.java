package org.example.smartinventory.controllers;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/register")
@CrossOrigin(value="http://localhost:3000")
public class RegistrationController
{
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService)
    {
        this.registrationService = registrationService;
    }

    @PostMapping("/")
    public ResponseEntity<?> registerNewUser(@RequestBody RegistrationDTO registration)
    {
        try
        {
            registrationService.registerUser(registration);
            return ResponseEntity.ok().build();

        }catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
