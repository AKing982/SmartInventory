package org.example.smartinventory.model;

import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.service.RoleService;
import org.example.smartinventory.workbench.security.permissions.PermissionCacheService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class SecurityUser implements UserDetails
{
    private final UserEntity user;
    private final Set<GrantedAuthority> authorities;
    private final Set<String> userPermissions = new HashSet<>();

    public SecurityUser(UserEntity user,
                        PermissionCacheService permissionCacheService,
                        RoleService roleService)
    {
        this.user = user;
        this.authorities = new HashSet<>();
        initializeRoleAndPermissions(user, roleService, permissionCacheService);
    }

    public void initializeRoleAndPermissions(UserEntity user, RoleService roleService, PermissionCacheService permissionCacheService){
        Set<String> userRoles = roleService.getUserRoles(user.getId());
        for (String role : userRoles) {
            authorities.add(new SimpleGrantedAuthority(role));
            Set<Permission> permissions = permissionCacheService.getPermissionsForRole(role);
            for (Permission permission : permissions) {
                userPermissions.add(permission.name());
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String toString() {
        return "SecurityUser{" +
                "user=" + user +
                ", authorities=" + authorities +
                ", permissions=" + userPermissions +
                '}';
    }
}
