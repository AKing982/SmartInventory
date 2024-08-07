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
    // Create
    UserEntity createUser(UserEntity user);
    List<UserEntity> createUsers(List<UserEntity> users);

    // Read
    Optional<UserEntity> getUserByUsername(String username);
    Optional<UserEntity> getUserByEmail(String email);
    Page<UserEntity> getAllUsersPaginated(Pageable pageable);

    // Update
    Optional<UserEntity> updateUser(UserEntity user);
    Optional<UserEntity> updateUserPassword(int userId, String newPassword);
    Optional<UserEntity> updateUserEmail(int userId, String newEmail);
    Optional<UserEntity> updateUserActiveStatus(int userId, boolean isActive);

    // Delete
    void deleteUser(int userId);
    void deleteUsers(List<Integer> userIds);

    // Search and Filter
    List<UserEntity> searchUsers(String keyword);
    List<UserEntity> getActiveUsers();
    List<UserEntity> getInactiveUsers();

    // Count
    long getUserCount();
    long getActiveUserCount();
    long getInactiveUserCount();

    // Validation
    boolean isUsernameUnique(String username);
    boolean isEmailUnique(String email);
    boolean isValidPassword(String password);

    // Authentication
    Optional<UserEntity> authenticateUser(String username, String password);
    void logoutUser(int userId);

    // Password Management
    void resetPassword(int userId);
    boolean changePassword(int userId, String oldPassword, String newPassword);

    // User Profile
    Optional<UserEntity> getUserProfile(int userId);
    Optional<UserEntity> updateUserProfile(int userId, UserEntity updatedProfile);

    // Export
    byte[] exportUsersToCsv();
    byte[] exportUsersToPdf();

    // Import
    List<UserEntity> importUsersFromCsv(byte[] csvData);

    // Sorting
    List<UserEntity> getAllUsersSorted(String sortBy, String sortOrder);

    // Analytics
    List<UserEntity> getMostActiveUsers(int limit);
    List<UserEntity> getRecentlyCreatedUsers(int limit);

    // Time-based queries
    List<UserEntity> getUsersCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<UserEntity> getUsersLastLoginBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Bulk operations
    int bulkDeactivateUsers(List<Integer> userIds);
    int bulkActivateUsers(List<Integer> userIds);

    // Roles and Permissions (assuming you might add these later)
    void assignRoleToUser(int userId, String roleName);
    void removeRoleFromUser(int userId, String roleName);
    List<String> getUserRoles(int userId);
    boolean hasPermission(int userId, String permission);

    // User Sessions
    void createUserSession(int userId, String sessionToken);
    void invalidateUserSession(int userId);
    boolean isUserSessionValid(int userId, String sessionToken);

    // Reporting
    byte[] generateUserActivityReport(LocalDateTime startDate, LocalDateTime endDate);

    // Notifications
    void sendPasswordResetNotification(int userId);
    void sendAccountLockedNotification(int userId);

    // Account Management
    void lockUserAccount(int userId);
    void unlockUserAccount(int userId);
    boolean isAccountLocked(int userId);

    // User Preferences
    void saveUserPreferences(int userId, Map<String, String> preferences);
    Map<String, String> getUserPreferences(int userId);

    // Advanced search
    Page<UserEntity> advancedSearch(String firstName, String lastName, String email, Boolean isActive, Pageable pageable);

    // Batch operations
    void batchCreateUsers(List<UserEntity> users);
    void batchUpdateUsers(List<UserEntity> users);

    // User statistics
//    UserStatistics getUserStatistics(int userId);
}
