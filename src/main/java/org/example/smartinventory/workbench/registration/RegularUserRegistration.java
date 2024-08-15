package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.RoleService;
import org.example.smartinventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class RegularUserRegistration implements RegistrationStrategy<UserEntity>
{
    private UserService userService;

    @Autowired
    public RegularUserRegistration(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String getRole() {
        return "ROLE_USER";
    }

    @Override
    public Optional<UserEntity> register(Registration registration, PermissionsService permissionsService) {
        if(registration == null || permissionsService == null){
            throw new IllegalArgumentException("Registration cannot be null");
        }
        // Create the user
        UserEntity user = createUserEntity(registration);
        Set<Permission> permissions = getPermissions(permissionsService, user);
        Set<RoleEntity> roles = Set.of(createRoleEntity(permissions));
        user.setRoles(roles);
        // Create the roles for the user
        return Optional.of(user);
    }


    private RoleEntity createRoleEntity(Set<Permission> permissions) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(getRole());
        roleEntity.setPermissions(permissions);
        return roleEntity;
    }

    private UserEntity createUserEntity(Registration registration)
    {
        return userService.createUserFromRegistration(registration);
    }

    public Set<Permission> getPermissions(PermissionsService permissionsService, UserEntity userEntity) {
        if(permissionsService == null || userEntity == null){
            throw new IllegalArgumentException("Permissions service cannot be null");
        }
        return permissionsService.getPermissionsForUser(userEntity);
    }
}
