package org.example.smartinventory.service;

import org.example.smartinventory.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends ServiceModel<UserEntity>
{
    Optional<UserEntity> findByUserNameOrEmail(String userName, String email);

    // Create
    void createUser(UserEntity user);

    // Read
    Optional<UserEntity> getUserByUsername(String username);
    Optional<UserEntity> getUserByEmail(String email);
    Page<UserEntity> getAllUsersPaginated(Pageable pageable);

    // Update
    int updateUser(UserEntity user);
    int updateUserPassword(int userId, String newPassword);
    int updateUserActiveStatus(int userId, boolean isActive);

    // Delete
    void deleteUser(int userId);

    // Search and Filter
    List<UserEntity> searchUsers(String keyword);
    List<UserEntity> getActiveUsers();

    // Count
    long getUserCount();

    // Validation
    boolean isUsernameUnique(String username);
    boolean isEmailUnique(String email);

    // Authentication
    Optional<UserEntity> authenticateUser(String username, String password);
    void logoutUser(int userId);

    // User Profile
    Optional<UserEntity> getUserProfile(int userId);
    Optional<UserEntity> updateUserProfile(int userId, UserEntity updatedProfile);

    // Roles and Permissions
    void assignRoleToUser(int userId, String roleName);
    List<String> getUserRoles(int userId);
    boolean hasPermission(int userId, String permission);
}
