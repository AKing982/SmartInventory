package org.example.smartinventory.service;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService
{
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void save(RoleEntity roleEntity) {
        roleRepository.save(roleEntity);
    }

    @Override
    public void delete(RoleEntity roleEntity) {
        roleRepository.delete(roleEntity);
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Collection<RoleEntity> findAllById(Iterable<Long> ids) {
        return roleRepository.findAllById(ids);
    }
}
