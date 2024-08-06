package org.example.smartinventory.service;

import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<UserEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(UserEntity userEntity) {

    }

    @Override
    public void delete(UserEntity userEntity) {

    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<UserEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
