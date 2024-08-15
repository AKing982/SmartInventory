package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.service.ManagerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ManagerRegistrationTest {

    @Mock
    private ManagerService managerService;

    @InjectMocks
    private ManagerRegistration managerRegistration;

    @BeforeEach
    void setUp() {
    }



    @AfterEach
    void tearDown() {
    }
}