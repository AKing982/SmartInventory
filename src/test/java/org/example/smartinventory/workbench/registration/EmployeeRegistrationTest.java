package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.EmployeeService;
import org.example.smartinventory.service.PermissionsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EmployeeRegistrationTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeRegistration employeeRegistration;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRegister_whenRegistrationNull(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        assertThrows(IllegalArgumentException.class, () -> employeeRegistration.register(null, mockPermissionsService));
    }

    @Test
    void testRegister_whenPermissionsServiceNull(){
        Registration mockRegistration = mock(Registration.class);
        assertThrows(IllegalArgumentException.class, () -> employeeRegistration.register(mockRegistration, null));
    }

    @Test
    void testRegister_whenPermissionsServiceAndRegistrationNull(){
        assertThrows(IllegalArgumentException.class, () -> employeeRegistration.register(null, null));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRegistrationParams")
    void testRegister_withInvalidRegistrationParams(Registration registration, String testCase){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        assertThrows(IllegalArgumentException.class,
                () -> employeeRegistration.register(registration, mockPermissionsService),
                "Failed for test case: " + testCase);
    }

    private static Stream<Object[]> provideInvalidRegistrationParams() {
        return Stream.of(
                new Object[]{null, "Null registration"},
                new Object[]{createRegistration(null, "role", "company", "password", "firstName", "lastName", "username", "jobTitle"), "Null email"},
                new Object[]{createRegistration("", "role", "company", "password", "firstName", "lastName", "username", "jobTitle"), "Empty email"},
                new Object[]{createRegistration("email", null, "company", "password", "firstName", "lastName", "username", "jobTitle"), "Null role"},
                new Object[]{createRegistration("email", "", "company", "password", "firstName", "lastName", "username", "jobTitle"), "Empty role"},
                new Object[]{createRegistration("email", "role", null, "password", "firstName", "lastName", "username", "jobTitle"), "Null company"},
                new Object[]{createRegistration("email", "role", "", "password", "firstName", "lastName", "username", "jobTitle"), "Empty company"},
                new Object[]{createRegistration("email", "role", "company", null, "firstName", "lastName", "username", "jobTitle"), "Null password"},
                new Object[]{createRegistration("email", "role", "company", "", "firstName", "lastName", "username", "jobTitle"), "Empty password"},
                new Object[]{createRegistration("email", "role", "company", "password", null, "lastName", "username", "jobTitle"), "Null firstName"},
                new Object[]{createRegistration("email", "role", "company", "password", "", "lastName", "username", "jobTitle"), "Empty firstName"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", null, "username", "jobTitle"), "Null lastName"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "", "username", "jobTitle"), "Empty lastName"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", null, "jobTitle"), "Null username"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", "", "jobTitle"), "Empty username"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", "username", null), "Null jobTitle"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", "username", ""), "Empty jobTitle"}
        );
    }

    private static Registration createRegistration(String email, String role, String company, String password,
                                                   String firstName, String lastName, String username, String jobTitle) {
        Registration registration = new Registration();
        registration.setEmail(email);
        registration.setRole(role);
        registration.setCompany(company);
        registration.setPassword(password);
        registration.setFirstName(firstName);
        registration.setLastName(lastName);
        registration.setUsername(username);
        registration.setJobTitle(jobTitle);
        return registration;
    }


    @AfterEach
    void tearDown() {
    }
}