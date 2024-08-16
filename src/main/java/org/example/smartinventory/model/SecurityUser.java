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

    public SecurityUser(UserEntity user, PermissionCacheService permissionCacheService, RoleService roleService)
    {
        this.user = user;
        this.authorities = new HashSet<>();
        Set<String> userRoles = roleService.getUserRoles(user.getId());
        for (String role : userRoles) {
            authorities.add(new SimpleGrantedAuthority(role));
            Set<Permission> permissions = permissionCacheService.getPermissionsForRole(role);
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.name()));
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
}
