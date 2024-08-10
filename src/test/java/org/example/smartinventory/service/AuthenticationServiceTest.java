package org.example.smartinventory.service;

import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.AuthenticationResponse;
import org.example.smartinventory.workbench.security.jwt.JWTUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsLoginService userDetailsService;

    @Mock
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mock
    private JWTUtil jwtUtil;

    @Spy
    @InjectMocks
    private AuthenticationService authenticationService;

    private final String USERNAME = "testUser";
    private final String PASSWORD = "testPassword";
    private final String EMAIL = "test@test.com";
    private final String ENCODED_PASSWORD = "$2a$10$c6CxGmyZeKURyT7vgSwIpeS5NCZ9.W6/UQjUFVPDCK8eeekhg03Ne";

    private UserDetails mockUserDetails;

    private Authentication mockAuthentication;

    @BeforeEach
    void setUp() {

        mockUserDetails = User.builder()
                .username(USERNAME)
                .password(ENCODED_PASSWORD)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        mockAuthentication = new UsernamePasswordAuthenticationToken(mockUserDetails, PASSWORD, mockUserDetails.getAuthorities());
    }

    @Test
    void testAuthenticate() {

        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(mockUserDetails);

        Authentication result = authenticationService.authenticate(mockAuthentication);

        assertNotNull(result);
        assertTrue(result.isAuthenticated());
        assertEquals(USERNAME, result.getName());
        assertEquals(PASSWORD, result.getCredentials());
        assertEquals(mockUserDetails, result.getPrincipal());

        verify(userDetailsService).loadUserByUsername(USERNAME);

    }

    @Test
    void testAuthenticateWithInvalidPassword() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        UserDetails mockUserDetails = User.builder()
                .username(username)
                .password(ENCODED_PASSWORD)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        when(userDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(authentication));

        // Verify
        verify(userDetailsService).loadUserByUsername(username);
    }

    @Test
    void testAuthenticateWithEmailAsUsername()
    {
        when(userDetailsService.loadUserByUsername(EMAIL)).thenReturn(mockUserDetails);

        Authentication authentication = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD);
        Authentication result = authenticationService.authenticate(authentication);

        assertNotNull(result);
        assertTrue(result.isAuthenticated());
        assertEquals(USERNAME, result.getName());
        assertEquals(PASSWORD, result.getCredentials());

        verify(userDetailsService).loadUserByUsername(EMAIL);
    }

    @Test
    void testCreateAuthenticationResponse()
    {
        String username = "testUser";
        String password = "testPassword";
        String token = "testJwtToken";

        doReturn(mockUserDetails).when(authenticationService).loadUserDetails(username);
        when(jwtUtil.generateToken(mockUserDetails)).thenReturn(token);

        AuthenticationResponse response = authenticationService.createAuthenticationResponse(username, password);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(username, response.getUsername());
        assertEquals(mockUserDetails.getAuthorities(), response.getRoles());

        // Verify
        verify(authenticationService).authenticate(any(Authentication.class));
    }



    @AfterEach
    void tearDown() {
    }
}