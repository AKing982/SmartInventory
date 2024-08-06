package org.example.smartinventory.service;

import org.example.smartinventory.entities.ContactEntity;
import org.example.smartinventory.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService
{
    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository)
    {
        this.contactRepository = contactRepository;
    }

    @Override
    public Collection<ContactEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(ContactEntity contactEntity) {

    }

    @Override
    public void delete(ContactEntity contactEntity) {

    }

    @Override
    public Optional<ContactEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<ContactEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
