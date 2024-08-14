package org.example.smartinventory.service;

import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
        testUser = new UserEntity();
        testUser.setUsername("test");
        testUser.setPassword("test");
        testUser.setFirstName("test");
        testUser.setLastName("test");
        testUser.setEmail("test@test.com");
        testUser.setId(1L);
    }

    @Test
    void testFindByUserNameOrEmail() {
        String userName = "john";
        String email = "john@example.com";
        UserEntity user = new UserEntity();
        Mockito.when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<UserEntity> result1 = userService.findByUserNameOrEmail(userName, "");
        Optional<UserEntity> result2 = userService.findByUserNameOrEmail("", email);

        Assertions.assertEquals(Optional.of(user), result1);
        Assertions.assertEquals(Optional.of(user), result2);
    }

    @Test
    void testGetUserByUserName(){
        String username = "test";
        UserEntity user = testUser;
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.getUserByUsername(username);

        Assertions.assertEquals(Optional.of(user), result);
    }

    @Test
    void testGetUserByEmail(){
        String email = "test@test.com";
        UserEntity user = testUser;
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.getUserByEmail(email);

        Assertions.assertEquals(Optional.of(user), result);
    }

    @Test
    void testFindAll()
    {
        List<UserEntity> userList = new ArrayList<>();
        userList.add(testUser);
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        Collection<UserEntity> result = userService.findAll();

        Assertions.assertEquals(userList, result);
    }

    @Test
    void testSave()
    {
        UserEntity savedUser = new UserEntity();
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(savedUser);

        userService.save(testUser);

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testFindById()
    {
        Long id = 1L;
        UserEntity user = testUser;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.findById(id);

        Assertions.assertEquals(Optional.of(user), result);
    }

    @Test
    void testFindAllById()
    {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<UserEntity> userList = new ArrayList<>();
        userList.add(testUser);
        Mockito.when(userRepository.findAllById(ids)).thenReturn(userList);

        Collection<UserEntity> result = userService.findAllById(ids);

        Assertions.assertEquals(userList, result);
    }



    @AfterEach
    void tearDown() {
    }
}