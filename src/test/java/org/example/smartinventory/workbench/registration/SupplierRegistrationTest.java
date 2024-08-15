package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.SupplierService;
import org.example.smartinventory.workbench.security.permissions.PermissionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierRegistrationTest {

    @Mock
    private SupplierService supplierService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SupplierRegistration supplierRegistration;

    private Registration testRegistration;

    @BeforeEach
    void setUp() {
        testRegistration = new Registration("first", "last", "test@test.com", "testUser", "testPassword", "supplier", "testCompany", "testCompany");
    }

    @Test
    void testRegister_whenRegistrationNull(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        assertThrows(IllegalArgumentException.class, () -> supplierRegistration.register(null, mockPermissionsService));
    }

    @Test
    void testRegister_whenPermissionsServiceNull(){
        Registration mockRegistration = mock(Registration.class);
        assertThrows(IllegalArgumentException.class, () -> supplierRegistration.register(mockRegistration, null));
    }

    @Test
    void testRegister_whenPermissionsServiceAndRegistrationNull(){
        assertThrows(IllegalArgumentException.class, () -> supplierRegistration.register(null, null));
    }

    @Test
    void testRegister_whenPermissionsServiceAndRegistration(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        Registration mockRegistration = Registration.builder()
                .firstName("first")
                .lastName("last")
                .email("test@test.com")
                .termsAgreed(true)
                .role("ROLE_SUPPLIER")
                .jobTitle("supplier")
                .username("testUser")
                .password("password")
                .company("testCompany")
                .build();

        UserEntity mockUser = createUser();
        when(supplierService.createSupplierFromRegistration(mockRegistration)).thenReturn(createSupplierEntity());
        doNothing().when(supplierService).save(any(SupplierEntity.class));

        Set<Permission> permissions = createPermissionSet();
        Optional<SupplierEntity> actualSupplier = supplierRegistration.register(mockRegistration, mockPermissionsService);
        Set<RoleEntity> roles = actualSupplier.get().getUser().getRoles();
        Set<Permission> actualPermissions = PermissionUtil.extractPermissionFromRoles(roles);
        assertNotNull(actualPermissions);
        assertTrue(actualSupplier.isPresent());
        assertEquals("ROLE_SUPPLIER", actualSupplier.get().getEmployeeRole().name());
        assertTrue(actualPermissions.containsAll(permissions));
    }

    @Test
    void testGetPermissions_whenSupplierEntityNull(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        assertThrows(IllegalArgumentException.class, () -> supplierRegistration.getPermissions(null, mockPermissionsService));
    }

    @Test
    void testGetPermissions_whenPermissionsServiceNull(){
        SupplierEntity mockSupplier = mock(SupplierEntity.class);
        assertThrows(IllegalArgumentException.class, () -> supplierRegistration.getPermissions(mockSupplier, null));
    }

    @Test
    void testGetPermissions_whenSupplierAndPermissionsNul(){
        assertThrows(IllegalArgumentException.class, () -> supplierRegistration.getPermissions(null, null));
    }

    @Test
    void testGetPermissions_whenSupplierAndPermissionsValid(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        SupplierEntity mockSupplier = createSupplierEntity();
        Set<RoleEntity> roles = mockSupplier.getUser().getRoles();
        Set<Permission> permissions = PermissionUtil.extractPermissionFromRoles(roles);
        when(mockPermissionsService.getPermissionsForUser(createUser())).thenReturn(permissions);

        Set<Permission> actualPermissions = supplierRegistration.getPermissions(mockSupplier, mockPermissionsService);
        assertNotNull(actualPermissions);
        assertEquals(permissions.size(), actualPermissions.size());
        assertTrue(actualPermissions.containsAll(permissions));
    }


    @Test
    void testGetPermissions_whenUserIsNull_thenThrowException(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        SupplierEntity mockSupplier = createSupplierEntityWithNullUser();

        assertThrows(IllegalArgumentException.class, () -> supplierRegistration.getPermissions(mockSupplier, mockPermissionsService));
    }

    private SupplierEntity createSupplierEntityWithNullUser(){
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setSupplierId(1);
        supplierEntity.setUser(null);
        supplierEntity.setSupplierName("testSupplier");
        supplierEntity.setEmployeeRole(EmployeeRole.ROLE_SUPPLIER);
        return supplierEntity;
    }

    private SupplierEntity createSupplierEntity() {
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setSupplierId(1);
        supplierEntity.setUser(createUser());
        supplierEntity.setSupplierName("testSupplier");
        supplierEntity.setEmployeeRole(EmployeeRole.ROLE_SUPPLIER);
        return supplierEntity;
    }

    private RoleEntity createRoleEntity(String role, Set<Permission> permissionSet){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        roleEntity.setPermissions(permissionSet);
        return roleEntity;
    }

    private Set<Permission> createPermissionSet(){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.VIEW_PRODUCT_CATALOG);
        permissions.add(Permission.CHECK_ITEM_AVAILABILITY);
        permissions.add(Permission.UPDATE_PRODUCT_INFORMATION);
        permissions.add(Permission.MANAGE_OWN_INVENTORY);
        permissions.add(Permission.VIEW_PURCHASE_ORDERS);
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
        RoleEntity roleEntity = createRoleEntity("ROLE_SUPPLIER", createPermissionSet());
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);
        user.setRoles(roles);
        return user;
    }

    @AfterEach
    void tearDown() {
    }
}