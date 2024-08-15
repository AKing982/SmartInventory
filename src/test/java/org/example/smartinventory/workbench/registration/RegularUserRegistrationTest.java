package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.UserService;
import org.example.smartinventory.workbench.security.permissions.PermissionFactory;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegularUserRegistrationTest {

    @Mock
    private UserService userService;

    @Mock
    private PermissionsService permissionsService;

    @InjectMocks
    private RegularUserRegistration regularUserRegistration;

    private Registration registration;

    @BeforeEach
    void setUp() {
        registration = Registration.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("password")
                .role("regular")
                .company("Example Company")
                .jobTitle("Software Engineer")
                .build();
    }

    @Test
    void testRegister_WhenRegistrationIsNull_ShouldThrowException() {

        PermissionsService mockPermissionsService = mock(PermissionsService.class);

        assertThrows(IllegalArgumentException.class, () -> {
            regularUserRegistration.register(null, mockPermissionsService);
        });
    }

    @Test
    void testRegister_whenPermissionsServiceIsNull_ShouldThrowException() {
        Registration mockRegistration = mock(Registration.class);
        assertThrows(IllegalArgumentException.class, () -> {
            regularUserRegistration.register(mockRegistration, null);
        });
    }

    @Test
    void testRegister_whenRegistrationAndPermissionsServiceNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            regularUserRegistration.register(null, null);
        });
    }

    @Test
    void testRegister_whenRegistrationAndPermissionsServiceNotNull(){
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        Registration mockRegistration = Registration.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("password")
                .role("regular")
                .company("Example Company")
                .jobTitle("Software Engineer")
                .build();
        Set<Permission> userPermissions = new HashSet<>();
        userPermissions.add(Permission.VIEW_PRODUCT_CATALOG);
        userPermissions.add(Permission.CHECK_ITEM_AVAILABILITY);
        userPermissions.add(Permission.PLACE_ORDERS);
        userPermissions.add(Permission.VIEW_ORDER_HISTORY);
        userPermissions.add(Permission.UPDATE_USER_PROFILE);

        RoleEntity roleEntity = createRoleEntity("ROLE_USER", userPermissions);

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);

        Optional<UserEntity> createdUser = Optional.of(createUserEntity());
        when(userService.createUserFromRegistration(mockRegistration)).thenReturn(createUserEntity());
        when(mockPermissionsService.getPermissionsForUser(createUserEntity())).thenReturn(userPermissions);

        Optional<UserEntity> actualUser = regularUserRegistration.register(mockRegistration, mockPermissionsService);

        assertTrue(actualUser.isPresent());
        assertEquals(createdUser.get().getId(), actualUser.get().getId());
        assertEquals(createdUser.get().getUsername(), actualUser.get().getUsername());
        assertEquals(createdUser.get().getPassword(), actualUser.get().getPassword());
        assertEquals(roles.size(), actualUser.get().getRoles().size());
        assertTrue(actualUser.get().getRoles().containsAll(roles));
    }

    @Test
    void testGetPermissions_WhenPermissionsServiceIsNull_ShouldThrowException() {
        UserEntity mockUser = mock(UserEntity.class);
        assertThrows(IllegalArgumentException.class, () -> {
            regularUserRegistration.getPermissions(mockUser, null);
        });
    }

    @Test
    void testGetPermissions_whenUserIsNull_ShouldThrowException() {
        PermissionsService mockPermissionsService = mock(PermissionsService.class);
        assertThrows(IllegalArgumentException.class, () -> {
            regularUserRegistration.getPermissions(null, mockPermissionsService);
        });
    }

    @Test
    void testGetPermissions_whenPermissionsServiceValidAndUserValid_thenReturnPermissions(){
        Set<Permission> userPermissions = new HashSet<>();
        userPermissions.add(Permission.VIEW_PRODUCT_CATALOG);
        userPermissions.add(Permission.CHECK_ITEM_AVAILABILITY);
        userPermissions.add(Permission.PLACE_ORDERS);
        userPermissions.add(Permission.VIEW_ORDER_HISTORY);
        userPermissions.add(Permission.UPDATE_USER_PROFILE);

        when(permissionsService.getPermissionsForUser(createUserEntity())).thenReturn(userPermissions);

        Set<Permission> actualPermissions = regularUserRegistration.getPermissions(createUserEntity(), permissionsService);
        assertNotNull(actualPermissions);
        assertEquals(userPermissions, actualPermissions);
    }

    private RoleEntity createRoleEntity(String role, Set<Permission> permissions) {
        RoleEntity r = new RoleEntity();
        r.setRole(role);
        r.setPermissions(permissions);
        return r;
    }

    private UserEntity createUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setEmail("email");
        userEntity.setFirstName("firstName");
        userEntity.setLastName("lastName");
        userEntity.set_active(true);
        return userEntity;
    }


    @AfterEach
    void tearDown() {
    }
}