package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.RoleService;
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

    @Mock
    private RoleService roleService;

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
        // Mock services
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        RoleService mockRoleService = mock(RoleService.class);

        // Create mock registration
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

        // Create mock user and supplier
        UserEntity mockUser = createUser();
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockUser));
        SupplierEntity mockSupplier = createSupplierEntity();
        mockSupplier.setUser(mockUser);

        // Mock service behaviors
        when(supplierService.createSupplierFromRegistration(mockRegistration)).thenReturn(mockSupplier);
        doNothing().when(supplierService).save(any(SupplierEntity.class));

        // Mock role service behavior
        RoleEntity mockRole = new RoleEntity();
        mockRole.setId(1);
        mockRole.setRole("ROLE_SUPPLIER");
        Set<Permission> permissions = createPermissionSet();
        mockRole.setPermissions(permissions);
        when(roleService.findByName("ROLE_SUPPLIER")).thenReturn(Optional.of(mockRole));
        doNothing().when(mockRoleService).addRoleToUser(anyLong(), anyInt());

        // Mock permissions service behavior
        when(mockPermissionsService.getPermissionsForUser(any(UserEntity.class))).thenReturn(permissions);

        // Perform the registration
        Optional<SupplierEntity> actualSupplier = supplierRegistration.register(mockRegistration, mockPermissionsService);

        // Verify the results
        assertTrue(actualSupplier.isPresent());
        assertEquals("ROLE_SUPPLIER", actualSupplier.get().getEmployeeRole().name());

        // Verify that the role was added to the user
        verify(roleService).addRoleToUser(mockUser.getId(), mockRole.getId());

        // Verify that the permissions service was called to get permissions
        verify(mockPermissionsService).getPermissionsForUser(mockUser);

        // Verify that the supplier was saved
        verify(supplierService).save(mockSupplier);

        // Optionally, verify the permissions if needed
        Set<Permission> actualPermissions = mockPermissionsService.getPermissionsForUser(mockUser);
        assertNotNull(actualPermissions);
        assertEquals(permissions, actualPermissions);
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
        RoleService mockRoleService = mock(RoleService.class);

        UserEntity mockUser = createUser();
        SupplierEntity mockSupplier = createSupplierEntity();
        mockSupplier.setUser(mockUser);

        Set<Permission> permissions = createPermissionSet();

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
        return user;
    }

    @AfterEach
    void tearDown() {
    }
}