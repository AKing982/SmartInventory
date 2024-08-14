package org.example.smartinventory.controllers;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.RegistrationService;
import org.example.smartinventory.workbench.converter.RegistrationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/register")
@CrossOrigin(value="http://localhost:3000")
public class RegistrationController
{
    private RegistrationService registrationService;
    private RegistrationConverter registrationConverter;

    @Autowired
    public RegistrationController(RegistrationService registrationService)
    {
        this.registrationService = registrationService;
        this.registrationConverter = new RegistrationConverter();
    }

    @PostMapping("/")
    public ResponseEntity<?> registerNewUser(@RequestBody RegistrationDTO registration)
    {
        try
        {
            Registration registration1 = registrationConverter.convert(registration);
            String userRole = registration1.getRole();
            registrationService.register(userRole, registration1);
            return ResponseEntity.ok().build();

        }catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
