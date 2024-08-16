package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.workbench.registration.RegistrationFactory;
import org.example.smartinventory.workbench.registration.RegistrationStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private RegistrationFactory registrationFactory;

    @Mock
    private PermissionsService permissionsService;

    @Mock
    private RoleService roleService;

    @Mock
    private SupplierService supplierService;

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
        when(userService.createUserFromRegistration(registrationDTO)).thenReturn(createUserEntity());

        Optional<UserEntity> userEntity = registrationService.createDefaultUser(registrationDTO);
        assertTrue(userEntity.isPresent());
        assertEquals(registrationDTO.getFirstName(), userEntity.get().getFirstName());
        assertEquals(registrationDTO.getLastName(), userEntity.get().getLastName());
        assertEquals(registrationDTO.getEmail(), userEntity.get().getEmail());
        assertEquals(registrationDTO.getPassword(), userEntity.get().getPassword());
    }

    @Test
    void testRegister_whenRoleIsEmpty_thenThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            registrationService.register("", registrationDTO);
        });
    }

    @Test
    void testRegister_whenRegistrationIsNull_thenThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            registrationService.register("ROLE_ADMIN", null);
        });
    }

    @Test
    void testRegister_whenRoleDoesntContainROLE_thenThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            registrationService.register("ADMIN", registrationDTO);
        });
    }

    @Test
    void testRegister_whenRoleEqualsEmployee_returnEmployee(){
        String role = "ROLE_EMPLOYEE";
        Registration registrationDTO = new Registration("first", "last", "test@test.com", "testUser", "testPassword", "testJob", "testRole", "testCompany");
        EmployeeEntity expectedEmployee = createEmployeeEntity();

        RegistrationStrategy<EmployeeEntity> strategy = mock(RegistrationStrategy.class);
        when(registrationFactory.getStrategy(role)).thenReturn(strategy);
        when(strategy.register(eq(registrationDTO), any(PermissionsService.class)))
                .thenReturn(Optional.of(expectedEmployee));

        // Act
        Optional<EmployeeEntity> actualEmployeeOptional = registrationService.register(role, registrationDTO);

        // Assert
        assertTrue(actualEmployeeOptional.isPresent());
        EmployeeEntity actualEmployee = actualEmployeeOptional.get();

        assertEquals(expectedEmployee.getRole(), actualEmployee.getRole());
        assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        assertEquals(expectedEmployee.getUser().getEmail(), actualEmployee.getUser().getEmail());
        assertEquals(expectedEmployee.getUser().getFirstName(), actualEmployee.getUser().getFirstName());
        assertEquals(expectedEmployee.getUser().getLastName(), actualEmployee.getUser().getLastName());
        assertEquals(expectedEmployee.getJobTitle(), actualEmployee.getJobTitle());
        assertEquals(expectedEmployee.getUser().getId(), actualEmployee.getUser().getId());

        verify(registrationFactory).getStrategy(role);
        verify(strategy).register(eq(registrationDTO), any(PermissionsService.class));
    }

    @Test
    void testRegister_whenRoleEqualsUser_returnUser(){
        String role = "ROLE_USER";
        Registration registration = new Registration("first", "last", "test@test.com", "testUser", "testPassword", "testJob", "testRole", "testCompany");
        UserEntity expectedUser = createUserEntity();

        RegistrationStrategy<UserEntity> strategy = mock(RegistrationStrategy.class);
        when(registrationFactory.getStrategy(role)).thenReturn(strategy);
        when(strategy.register(eq(registration), any(PermissionsService.class)))
                .thenReturn(Optional.of(expectedUser));

        // Act
        Optional<UserEntity> actualUserOptional = registrationService.register(role, registration);

        // Assert
        assertTrue(actualUserOptional.isPresent());
        UserEntity actualUser = actualUserOptional.get();

        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.is_active(), actualUser.is_active());
    }

    @Test
    void testRegister_whenRoleEqualsSupplier_returnSupplier(){
        String role = "ROLE_SUPPLIER";
        Registration registration = new Registration("Mike", "Adams", "madams@outlook.com", "MAdams52", "Gamer25!", "ROLE_SUPPLIER", "Carpenter Utilities", "Supplier Specialist");
        UserEntity userEntity = createUser("Mike", "Adams", "madams@outlook.com", "Gamer25!");
        SupplierEntity supplier = createSupplier("Carpenter Utilities", null);

        RegistrationStrategy<SupplierEntity> strategy = mock(RegistrationStrategy.class);
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.empty());
        when(registrationFactory.getStrategy(role)).thenReturn(strategy);
        when(strategy.register(eq(registration), any(PermissionsService.class)))
                .thenReturn(Optional.of(supplier));

        when(supplierService.createSupplierFromRegistration(registration, userEntity))
                .thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> {
            registrationService.register(role, registration);
        });

        when(supplierService.createSupplierFromRegistration(registration, userEntity)).thenReturn(supplier);

        Optional<SupplierEntity> actualSupplierOptional = registrationService.register(role, registration);
        assertTrue(actualSupplierOptional.isPresent());
        SupplierEntity actualSupplier = actualSupplierOptional.get();

        assertEquals(supplier.getSupplierName(), actualSupplier.getSupplierName());
        assertEquals(supplier.getEmployeeRole(), actualSupplier.getEmployeeRole());
        assertEquals(supplier.getUser().getFirstName(), actualSupplier.getUser().getFirstName());
        assertEquals(supplier.getUser().getLastName(), actualSupplier.getUser().getLastName());
        assertEquals(supplier.getUser().getEmail(), actualSupplier.getUser().getEmail());
    }

    @Test
    void testRegister_whenRoleEqualsSupplier_throwNoSuchElementException() {
        String role = "ROLE_SUPPLIER";
        Registration registration = new Registration("Mike", "Adams", "madams@outlook.com", "MAdams52", "Gamer25!", "ROLE_SUPPLIER", "Carpenter Utilities", "Supplier Specialist");

        RegistrationStrategy<SupplierEntity> strategy = mock(RegistrationStrategy.class);
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.empty());
        when(registrationFactory.getStrategy(role)).thenReturn(strategy);

        // Return an empty Optional to simulate the condition leading to NoSuchElementException
        when(strategy.register(eq(registration), any(PermissionsService.class)))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            registrationService.register(role, registration);
        });

        verify(strategy).register(eq(registration), any(PermissionsService.class));
    }

    private SupplierEntity createSupplier(String supplierName, UserEntity userEntity) {
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setSupplierName(supplierName);
        supplierEntity.setUser(userEntity);
        supplierEntity.setEmployeeRole(EmployeeRole.ROLE_SUPPLIER);
        return supplierEntity;
    }

    private RegistrationStrategy createStrategy(String role){
        return registrationFactory.getStrategy(role);
    }

    private EmployeeEntity createEmployeeEntity(){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1L);
        employeeEntity.setJobTitle("testJob");
        employeeEntity.setUser(createUserEntity());
        employeeEntity.setRole(EmployeeRole.INVENTORY_MANAGER);
        return employeeEntity;
    }

    private UserEntity createUser(String first, String last, String email, String password){
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(first);
        userEntity.setLastName(last);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        return userEntity;
    }

    private UserEntity createUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("first");
        userEntity.setLastName("last");
        userEntity.setEmail("test@test.com");
        userEntity.setPassword("testPassword");
        return userEntity;
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