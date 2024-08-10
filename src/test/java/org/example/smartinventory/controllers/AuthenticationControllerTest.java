package org.example.smartinventory.controllers;

import org.example.smartinventory.config.JpaConfig;
import org.example.smartinventory.model.AuthenticationResponse;
import org.example.smartinventory.model.Login;
import org.example.smartinventory.service.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value=AuthenticationController.class, excludeAutoConfiguration= SecurityAutoConfiguration.class)
@Import({JpaConfig.class})
@RunWith(SpringRunner.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    private final String testUser = "testUser";
    private final String testPassword = "testPassword";

    @BeforeEach
    void setUp() {
        AuthenticationResponse mockResponse = new AuthenticationResponse("mockToken", "Bearer", testUser, Collections.emptyList());
        when(authenticationService.createAuthenticationResponse(testUser, testPassword)).thenReturn(mockResponse);
    }

    @Test
    void shouldAuthenticateUser() throws Exception {

        Login login = new Login(testUser, testPassword);
        mockMvc.perform(post("/api/auth/login")
                .content(asJsonString(login))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockToken"))
                .andDo(print());
    }

    private static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
    }
}