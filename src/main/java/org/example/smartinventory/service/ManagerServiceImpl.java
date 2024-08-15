package org.example.smartinventory.service;

import org.example.smartinventory.entities.ManagerEntity;
import org.example.smartinventory.repository.ManagerRepository;
import org.example.smartinventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService
{
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository,
                              UserRepository userRepository)
    {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<ManagerEntity> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public void save(ManagerEntity managerEntity) {
        managerRepository.save(managerEntity);
    }

    @Override
    public void delete(ManagerEntity managerEntity) {
        managerRepository.delete(managerEntity);
    }

    @Override
    public Optional<ManagerEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<ManagerEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }

    @Override
    public ManagerEntity createManagerFromRegistration(RegistrationService registrationService)
    {
        return null;
    }
}
