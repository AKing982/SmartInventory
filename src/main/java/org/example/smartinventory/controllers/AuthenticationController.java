package org.example.smartinventory.controllers;

import org.example.smartinventory.dto.LoginDTO;
import org.example.smartinventory.model.AuthenticationResponse;
import org.example.smartinventory.model.Login;
import org.example.smartinventory.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api/auth")
public class AuthenticationController
{
    private AuthenticationService authenticationService;
    private Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO login)
    {
        LOGGER.info("Username: {}", login.username());
        LOGGER.info("Password: {}", login.password());
        try
        {
            AuthenticationResponse authenticationResponse = authenticationService.createAuthenticationResponse(
                    login.username(), login.password()
            );
            return ResponseEntity.ok(authenticationResponse);
        }catch(BadCredentialsException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or password");
        }catch(Exception e)
        {
            LOGGER.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private AuthenticationResponse getAuthenticationResponse(String username, String password)
    {
        return authenticationService.createAuthenticationResponse(username, password);
    }


}
