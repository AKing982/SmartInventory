package org.example.smartinventory.model;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.RoleService;
import org.example.smartinventory.workbench.security.permissions.PermissionCacheService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class SecurityUserTest {

    private SecurityUser securityUser;
    private UserEntity testUser;
    private TestPermissionCacheService permissionCacheService;
    private TestRoleService roleService;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity(1L, "first", "last", "test@test.com", "user","password");
        permissionCacheService = new TestPermissionCacheService();
        roleService = new TestRoleService();

        securityUser = new SecurityUser(testUser, permissionCacheService, roleService);
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_SUPPLIER")));
    }

    @Test
    void testInitializeRoleAndPermissions_SingleRole() {
        UserEntity user = new UserEntity(1L, "user1", "password", "Test", "User", "test@example.com");
        TestPermissionCacheService permissionCacheService = new TestPermissionCacheService();
        TestRoleService roleService = new TestRoleService();

        SecurityUser securityUser = new SecurityUser(user, permissionCacheService, roleService);

        assertEquals(1, securityUser.getAuthorities().size());
        assertTrue(securityUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(securityUser.toString().contains("permissions=[VIEW_PRODUCT_CATALOG, CHECK_ITEM_AVAILABILITY, PLACE_ORDERS, VIEW_ORDER_HISTORY, UPDATE_USER_PROFILE]"));
    }

    @Test
    void testInitializeRoleAndPermissions_MultipleRoles() {
        UserEntity user = new UserEntity(1L, "user2", "password", "Test", "User", "test@example.com");
        TestPermissionCacheService permissionCacheService = new TestPermissionCacheService();
        TestRoleService roleService = new TestRoleService();

        SecurityUser securityUser = new SecurityUser(user, permissionCacheService, roleService);

        assertEquals(2, securityUser.getAuthorities().size());
        assertTrue(securityUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(securityUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPPLIER")));
        assertTrue(securityUser.toString().contains("permissions=[VIEW_PRODUCT_CATALOG, CHECK_ITEM_AVAILABILITY, PLACE_ORDERS, VIEW_ORDER_HISTORY, UPDATE_USER_PROFILE, UPDATE_PRODUCT_INFORMATION, MANAGE_OWN_INVENTORY, VIEW_PURCHASE_ORDERS]"));
    }

    @Test
    void testInitializeRoleAndPermissions_NoRoles() {
        UserEntity user = new UserEntity(1L, "user3", "password", "Test", "User", "test@example.com");
        TestPermissionCacheService permissionCacheService = new TestPermissionCacheService();
        TestRoleService roleService = new TestRoleService();

        SecurityUser securityUser = new SecurityUser(user, permissionCacheService, roleService);

        assertTrue(securityUser.getAuthorities().isEmpty());
        assertTrue(securityUser.toString().contains("permissions=[]"));
    }

    @Test
    void testInitializeRoleAndPermissions_UnknownRole() {
        UserEntity user = new UserEntity(1L, "user4", "password", "Test", "User", "test@example.com");
        TestPermissionCacheService permissionCacheService = new TestPermissionCacheService();
        TestRoleService roleService = new TestRoleService();

        SecurityUser securityUser = new SecurityUser(user, permissionCacheService, roleService);

        assertEquals(1, securityUser.getAuthorities().size());
        assertTrue(securityUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_UNKNOWN")));
        assertTrue(securityUser.toString().contains("permissions=[]"));
    }

    @Test
    void testInitializeRoleAndPermissions_MixedKnownUnknownRoles() {
        UserEntity user = new UserEntity(1L, "user5", "password", "Test", "User", "test@example.com");
        TestPermissionCacheService permissionCacheService = new TestPermissionCacheService();
        TestRoleService roleService = new TestRoleService();

        SecurityUser securityUser = new SecurityUser(user, permissionCacheService, roleService);

        assertEquals(2, securityUser.getAuthorities().size());
        assertTrue(securityUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(securityUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_UNKNOWN")));
        assertTrue(securityUser.toString().contains("permissions=[VIEW_PRODUCT_CATALOG, CHECK_ITEM_AVAILABILITY, PLACE_ORDERS, VIEW_ORDER_HISTORY, UPDATE_USER_PROFILE]"));
    }

    @Test
    void testGetPassword() {
        assertEquals("password", securityUser.getPassword());
    }

    @Test
    void testGetUsername() {
        assertEquals("testUser", securityUser.getUsername());
    }

    @Test
    void testToString() {
        String result = securityUser.toString();
        assertTrue(result.contains("user=" + testUser));
        assertTrue(result.contains("authorities=[ROLE_USER, ROLE_SUPPLIER]"));
        assertTrue(result.contains("permissions=[VIEW_PRODUCT_CATALOG, CHECK_ITEM_AVAILABILITY, PLACE_ORDERS, VIEW_ORDER_HISTORY, UPDATE_USER_PROFILE, UPDATE_PRODUCT_INFORMATION, MANAGE_OWN_INVENTORY, VIEW_PURCHASE_ORDERS]"));
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(securityUser.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(securityUser.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(securityUser.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(securityUser.isEnabled());
    }

    // Test implementations of dependencies

    private static class TestPermissionCacheService extends PermissionCacheService {
        @Override
        public Set<Permission> getPermissionsForRole(String role) {
            switch (role) {
                case "ROLE_USER":
                    return new HashSet<>(Arrays.asList(
                            Permission.VIEW_PRODUCT_CATALOG,
                            Permission.CHECK_ITEM_AVAILABILITY,
                            Permission.PLACE_ORDERS,
                            Permission.VIEW_ORDER_HISTORY,
                            Permission.UPDATE_USER_PROFILE
                    ));
                case "ROLE_SUPPLIER":
                    return new HashSet<>(Arrays.asList(
                            Permission.VIEW_PRODUCT_CATALOG,
                            Permission.CHECK_ITEM_AVAILABILITY,
                            Permission.UPDATE_PRODUCT_INFORMATION,
                            Permission.MANAGE_OWN_INVENTORY,
                            Permission.VIEW_PURCHASE_ORDERS
                    ));
                default:
                    return new HashSet<>();
            }
        }
    }

    private static class TestRoleService implements RoleService {
        @Override
        public Set<String> getUserRoles(Long userId) {
            return new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_SUPPLIER"));
        }

        @Override
        public Set<String> getUserRolePermissions(Long userId) {
            return Set.of();
        }

        @Override
        public void addRoleToUser(Long userId, Integer roleId) {

        }

        @Override
        public void removeRoleFromUser(Long userId, Integer roleId) {

        }

        @Override
        public Optional<RoleEntity> findByName(String name) {
            return Optional.empty();
        }

        @Override
        public Collection<RoleEntity> findAll() {
            return List.of();
        }

        @Override
        public void save(RoleEntity roleEntity) {

        }

        @Override
        public void delete(RoleEntity roleEntity) {

        }

        @Override
        public Optional<RoleEntity> findById(Long id) {
            return Optional.empty();
        }

        @Override
        public Collection<RoleEntity> findAllById(Iterable<Long> ids) {
            return List.of();
        }
    }

    @AfterEach
    void tearDown() {
    }
}