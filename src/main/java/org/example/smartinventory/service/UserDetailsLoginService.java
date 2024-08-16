package org.example.smartinventory.service;

import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.SecurityUser;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.workbench.security.permissions.PermissionCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsLoginService implements UserDetailsService
{
    private UserRepository userRepository;
    private final PermissionCacheService permissionCacheService;
    private final RoleService roleService;
    private Logger LOGGER = LoggerFactory.getLogger(UserDetailsLoginService.class);

    @Autowired
    public UserDetailsLoginService(UserRepository userRepository, PermissionCacheService permissionCacheService, RoleService roleService)
    {
        this.userRepository = userRepository;
        this.permissionCacheService = permissionCacheService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        // Does the 'username' contain the @ symbol
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = emailPattern.matcher(username);
        if(matcher.matches())
        {
            LOGGER.info("Username matches email pattern");
            Optional<UserEntity> user = userRepository.findByEmail(username);
            if (user.isPresent()) {
                LOGGER.info("User found");
                UserDetails userDetails = getUserDetails(user.get());
                LOGGER.info("UserDetails: {}", userDetails.toString());
                return getUserDetails(user.get());
            }
        }
        // Else find by username
        LOGGER.info("Username is a regular username");
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username: " + username + " not found"));
        return getUserDetails(user);
    }

    private SecurityUser getUserDetails(UserEntity user)
    {
        return new SecurityUser(user, permissionCacheService, roleService);
    }
}
