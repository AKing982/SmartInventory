package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.model.Registration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationService registrationService;

    private Registration registrationDTO;

    @BeforeEach
    void setUp() {
        registrationDTO = new Registration("first", "last", "test@test.com", "testUser", "testPassword", "testJob", "testRole", "testCompany");
    }

    @Test
    void testCreateDefaultUser_RegistrationIsNull_thenThrowException(){
        assertThrows(IllegalArgumentException.class, ()->{
            registrationService.createDefaultUser(null);
        });
    }

    @ParameterizedTest
    @MethodSource("nullParameterCombinations")
    void testCreateDefaultUser_RegistrationHasNullParameters_thenThrowException(Registration registration){
        assertThrows(IllegalArgumentException.class, () -> {
            registrationService.createDefaultUser(registration);
        });
    }

    @Test
    void testCreateDefaultUser_RegistrationHasValidParameters_thenReturnUser(){

    }

    private static Stream<Registration> nullParameterCombinations() {
        return Stream.of(
                new Registration(null, "last", "test@test.com", "testUser", "testPassword", "testJob", "testRole", "testCompany"),
                new Registration("first", null, "test@test.com", "testUser", "testPassword", "testJob", "testRole", "testCompany"),
                new Registration("first", "last", null, "testUser", "testPassword", "testJob", "testRole", "testCompany"),
                new Registration("first", "last", "test@test.com", null, "testPassword", "testJob", "testRole", "testCompany"),
                new Registration("first", "last", "test@test.com", "testUser", null, "testJob", "testRole", "testCompany"),
                new Registration("first", "last", "test@test.com", "testUser", "testPassword", null, "testRole", "testCompany"),
                new Registration("first", "last", "test@test.com", "testUser", "testPassword", "testJob", null, "testCompany"),
                new Registration("first", "last", "test@test.com", "testUser", "testPassword", "testJob", "testRole", null),
                new Registration(null, null, null, null, null, null, null, null)
        );
    }

    @AfterEach
    void tearDown() {
    }
}