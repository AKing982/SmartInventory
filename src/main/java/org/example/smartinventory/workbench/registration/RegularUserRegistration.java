package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.RoleService;
import org.example.smartinventory.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class RegularUserRegistration extends AbstractRegistrationBase<UserEntity> implements RegistrationStrategy<UserEntity>
{
    private UserService userService;
    private Logger LOGGER = LoggerFactory.getLogger(RegularUserRegistration.class);

    @Autowired
    public RegularUserRegistration(UserService userService, RoleService roleService)
    {
        super(roleService);
        this.userService = userService;
        LOGGER.debug("RoleService: {}", roleService);
    }

    @Override
    public final String getRole() {
        return "ROLE_USER";
    }

    @Override
    public Optional<UserEntity> register(Registration registration, PermissionsService permissionsService) {
        if(registration == null || permissionsService == null){
            throw new IllegalArgumentException("Registration cannot be null");
        }
        // Create the user
        UserEntity user = createUserEntity(registration);
        Set<Permission> permissions = getPermissions(user, permissionsService);
        addRoleToPermissions(permissions, getRole(), permissionsService);
        setRoleConfigurationForEntity(permissions, user);
        // Create the roles for the user
        return Optional.of(user);
    }

    private UserEntity createUserEntity(Registration registration)
    {
        return userService.createUserFromRegistration(registration);
    }

    @Override
    protected Set<Permission> getPermissions(UserEntity userEntity, PermissionsService permissionsService) {
        if(permissionsService == null || userEntity == null){
            throw new IllegalArgumentException("Permissions service cannot be null");
        }
        return permissionsService.getPermissionsForUser(userEntity);
    }

    @Override
    protected void setRoleConfigurationForEntity(Set<Permission> permissions, UserEntity entity) {
        RoleEntity role = roleService.findByName(getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        roleService.addRoleToUser(entity.getId(), role.getId());

        Set<Permission> rolePermissions = new HashSet<>(role.getPermissions());
        if(!rolePermissions.equals(permissions)){
           LOGGER.warn("Provided permissions for role {} do not match existing permissions." +
                   "No changes were made to the role.", getRole());
        }
    }
}
