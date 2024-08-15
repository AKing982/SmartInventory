package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.*;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.EmployeeService;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.workbench.security.permissions.PermissionUtil;
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
import org.testcontainers.shaded.org.bouncycastle.util.io.pem.PemHeader;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void testRegister_withInvalidRegistrationParams(Registration registration, Class<? extends Exception> expectedExceptionClass, String testCase) {
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        assertThrows(expectedExceptionClass,
                () -> employeeRegistration.register(registration, mockPermissionsService),
                "Failed for test case: " + testCase);
    }

    @Test
    void testGetPermissions_whenEmployeeEntityNull(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        assertThrows(IllegalArgumentException.class, () -> {
            employeeRegistration.getPermissions(null, mockPermissionsService);
        });
    }

    @Test
    void testGetPermissions_whenPermissionsServiceNull(){
        EmployeeEntity mockEmployee = mock(EmployeeEntity.class);
        assertThrows(IllegalArgumentException.class, () -> {
            employeeRegistration.getPermissions(mockEmployee, null);
        });
    }

    @Test
    void testGetPermissions_whenPermissionServiceAndEmployeeEntityNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            employeeRegistration.getPermissions(null, null);
        });
    }

    @Test
    void testGetPermissions_whenEmployeeEntityAndPermissionsServiceValid(){
        PermissionsService permissionsService = mock(PermissionsService.class);

        EmployeeEntity employeeEntity = createEmployee();
        Set<RoleEntity> roles = employeeEntity.getUser().getRoles();
        Set<Permission> expectedPermissions = PermissionUtil.extractPermissionFromRoles(roles);

        when(permissionsService.getPermissionsForUser(createUser())).thenReturn(expectedPermissions);

        Set<Permission> actualPermissions = employeeRegistration.getPermissions(employeeEntity, permissionsService);
        assertEquals(expectedPermissions, actualPermissions);
        assertNotNull(actualPermissions);
        assertEquals(expectedPermissions.size(), actualPermissions.size());
    }

    @Test
    void testGetPermissions_whenUserEntityIsNull_thenThrowException(){
        PermissionsService permissionsService = mock(PermissionsService.class);

        EmployeeEntity employee = createEmployeeWithNullUser();
        assertThrows(IllegalArgumentException.class, () -> employeeRegistration.getPermissions(employee, permissionsService));
    }

    @Test
    void testRegister_whenRegistrationAndPermissionsValid(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        Registration mockRegistration = Registration.builder()
                .firstName("first")
                .lastName("last")
                .email("test@test.com")
                .termsAgreed(true)
                .role("ROLE_EMPLOYEE")
                .jobTitle("testJob")
                .username("testUser")
                .password("password")
                .company("testCompany")
                .build();

        UserEntity mockUser = createUser();
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockUser));

        doNothing().when(employeeService).save(any(EmployeeEntity.class));

        Set<Permission> expectedPermissions = createPermissionSet();
        Optional<EmployeeEntity> actualEmployee = employeeRegistration.register(mockRegistration, mockPermissionsService);
        Set<RoleEntity> roles = actualEmployee.get().getUser().getRoles();
        Set<Permission> actualPermissions = PermissionUtil.extractPermissionFromRoles(roles);
        assertTrue(actualEmployee.isPresent());
        assertEquals("ROLE_EMPLOYEE", actualEmployee.get().getRole().name());
        assertEquals("test@test.com", actualEmployee.get().getEmail());
        assertEquals("first", actualEmployee.get().getEmpFirstName());
        assertEquals("last", actualEmployee.get().getEmpLastName());
        assertTrue(actualPermissions.containsAll(expectedPermissions));

        verify(userRepository).findByEmail("test@test.com");
        verify(employeeService).save(any(EmployeeEntity.class));
    }


    private static Stream<Object[]> provideInvalidRegistrationParams() {
        return Stream.of(
                new Object[]{createRegistration(null, "role", "company", "password", "firstName", "lastName", "username", "jobTitle"), NullPointerException.class, "Null email"},
                new Object[]{createRegistration("", "role", "company", "password", "firstName", "lastName", "username", "jobTitle"), IllegalArgumentException.class, "Empty email"},
                new Object[]{createRegistration("email", null, "company", "password", "firstName", "lastName", "username", "jobTitle"), NullPointerException.class, "Null role"},
                new Object[]{createRegistration("email", "", "company", "password", "firstName", "lastName", "username", "jobTitle"), IllegalArgumentException.class, "Empty role"},
                new Object[]{createRegistration("email", "role", null, "password", "firstName", "lastName", "username", "jobTitle"), NullPointerException.class, "Null company"},
                new Object[]{createRegistration("email", "role", "", "password", "firstName", "lastName", "username", "jobTitle"), IllegalArgumentException.class, "Empty company"},
                new Object[]{createRegistration("email", "role", "company", null, "firstName", "lastName", "username", "jobTitle"), NullPointerException.class, "Null password"},
                new Object[]{createRegistration("email", "role", "company", "", "firstName", "lastName", "username", "jobTitle"), IllegalArgumentException.class, "Empty password"},
                new Object[]{createRegistration("email", "role", "company", "password", null, "lastName", "username", "jobTitle"), NullPointerException.class, "Null firstName"},
                new Object[]{createRegistration("email", "role", "company", "password", "", "lastName", "username", "jobTitle"), IllegalArgumentException.class, "Empty firstName"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", null, "username", "jobTitle"), NullPointerException.class, "Null lastName"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "", "username", "jobTitle"), IllegalArgumentException.class, "Empty lastName"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", null, "jobTitle"), NullPointerException.class, "Null username"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", "", "jobTitle"), IllegalArgumentException.class, "Empty username"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", "username", null), NullPointerException.class, "Null jobTitle"},
                new Object[]{createRegistration("email", "role", "company", "password", "firstName", "lastName", "username", ""), IllegalArgumentException.class, "Empty jobTitle"}
        );
    }

    private RoleEntity createRoleEntity(String role, Set<Permission> permissionSet){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        roleEntity.setPermissions(permissionSet);
        return roleEntity;
    }

    private Set<Permission> createPermissionSet(){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.VIEW_INVENTORY);
        permissions.add(Permission.VIEW_ORDER_HISTORY);
        permissions.add(Permission.UPDATE_USER_PROFILE);
        permissions.add(Permission.GENERATE_REPORTS);
        permissions.add(Permission.PROCESS_ORDERS);
        permissions.add(Permission.UPDATE_INVENTORY);
        return permissions;
    }

    private UserEntity createUser(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.set_active(true);
        user.setFirstName("first");
        user.setLastName("last");
        user.setUsername("testUser");
        user.setEmail("test@test.com");
        user.setPassword("password");
        RoleEntity roleEntity = createRoleEntity("ROLE_EMPLOYEE", createPermissionSet());
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);
        user.setRoles(roles);
        return user;
    }

    private EmployeeEntity createEmployeeWithNullUser(){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setUser(null);
        employeeEntity.set_active(true);
        employeeEntity.setEmpLastName("empLast");
        employeeEntity.setRole(EmployeeRole.INVENTORY_MANAGER);
        employeeEntity.setEmpFirstName("empFirst");
        employeeEntity.setId(1L);
        return employeeEntity;
    }

    private EmployeeEntity createEmployee(){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setUser(createUser());
        employeeEntity.set_active(true);
        employeeEntity.setEmpLastName("empLast");
        employeeEntity.setEmpFirstName("empFirst");
        employeeEntity.setRole(EmployeeRole.ROLE_EMPLOYEE);
        employeeEntity.setId(1L);
        return employeeEntity;
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