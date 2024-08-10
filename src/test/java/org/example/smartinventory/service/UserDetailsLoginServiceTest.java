package org.example.smartinventory.service;

import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserDetailsLoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsLoginService userDetailsLoginService;

    private UserEntity testUser;

    private final String TEST_USERNAME = "testUser";
    private final String TEST_PASSWORD = "testPassword";
    private final String TEST_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUsername(TEST_USERNAME);
        testUser.setEmail(TEST_EMAIL);
        testUser.setPassword(new BCryptPasswordEncoder().encode(TEST_PASSWORD));
    }

    @Test
    void loadUserByUsername_WithValidUsername_ShouldReturnUserDetails() {
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(testUser));

        UserDetails userDetails = userDetailsLoginService.loadUserByUsername(TEST_USERNAME);

        assertNotNull(userDetails);
        assertEquals(TEST_USERNAME, userDetails.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(TEST_PASSWORD, userDetails.getPassword()));
        assertTrue(userDetails.getAuthorities().isEmpty());

        verify(userRepository).findByUsername(TEST_USERNAME);
        verify(userRepository, never()).findByEmail(anyString());
    }

    @Test
    void loadUserByUsername_WithValidEmail_ShouldReturnUserDetails() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

        UserDetails userDetails = userDetailsLoginService.loadUserByUsername(TEST_EMAIL);

        assertNotNull(userDetails);
        assertEquals(TEST_USERNAME, userDetails.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(TEST_PASSWORD, userDetails.getPassword()));
        assertTrue(userDetails.getAuthorities().isEmpty());

        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    void loadUserByUsername_WithInvalidUsername_ShouldThrowUsernameNotFoundException() {
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsLoginService.loadUserByUsername(TEST_USERNAME)
        );

        verify(userRepository).findByUsername(TEST_USERNAME);
        verify(userRepository, never()).findByEmail(anyString());
    }

    @Test
    void loadUserByUsername_WithInvalidEmail_ShouldThrowUsernameNotFoundException() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsLoginService.loadUserByUsername(TEST_EMAIL)
        );

        verify(userRepository).findByEmail(TEST_EMAIL);
    }

    @Test
    void loadUserByUsername_WithNonEmailNonUsername_ShouldTryFindByUsername() {
        String invalidInput = "not-an-email-or-username";
        when(userRepository.findByUsername(invalidInput)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsLoginService.loadUserByUsername(invalidInput)
        );

        verify(userRepository).findByUsername(invalidInput);
        verify(userRepository, never()).findByEmail(anyString());
    }

    @AfterEach
    void tearDown() {
    }
}