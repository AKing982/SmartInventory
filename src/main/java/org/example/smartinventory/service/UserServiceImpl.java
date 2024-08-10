package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Optional<UserEntity> findByUserNameOrEmail(String userName, String email) {
        if(userName.isEmpty())
        {
            return userRepository.findByEmail(email);
        }
        return userRepository.findByUsername(userName);
    }

    @Override
    @Transactional
    public UserEntity createUserFromRegistration(RegistrationDTO user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.email());
        String hashedPassword = passwordEncoder.encode(user.password());
        LOGGER.info("Hashed password: " + hashedPassword);
        userEntity.setPassword(hashedPassword);
        userEntity.setFirstName(user.firstName());
        userEntity.setLastName(user.lastName());
        userEntity.setUsername(user.username());
        LOGGER.info("Saving User from Registration: {} ", userEntity.toString());

        return userRepository.save(userEntity);
    }

    private String getEncodedPassword(String password)
    {
        return passwordEncoder.encode(password);
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public Collection<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void delete(UserEntity userEntity) {
        // NOT IMPLEMENTED
    }


    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Collection<UserEntity> findAllById(Iterable<Long> ids) {
        return userRepository.findAllById(ids);
    }
}
