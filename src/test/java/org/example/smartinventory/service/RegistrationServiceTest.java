package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationService registrationService;

    private RegistrationDTO registrationDTO;

    @BeforeEach
    void setUp() {
        registrationDTO = new RegistrationDTO("first", "last", "test@test.com", "testUser", "testPassword", "testJob", "testRole", "testCompany");
    }


    @AfterEach
    void tearDown() {
    }
}