package org.example.smartinventory.service;

import org.example.smartinventory.workbench.security.permissions.PermissionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PermissionsServiceTest {

    @Mock
    private PermissionFactory permissionFactory;

    @InjectMocks
    private PermissionsService permissionsService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}