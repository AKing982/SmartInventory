package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends ServiceModel<UserEntity>
{
    Optional<UserEntity> findByUserNameOrEmail(String userName, String email);

    UserEntity createUserFromRegistration(Registration user);

    // Read
    Optional<UserEntity> getUserByUsername(String username);
    Optional<UserEntity> getUserByEmail(String email);

}
