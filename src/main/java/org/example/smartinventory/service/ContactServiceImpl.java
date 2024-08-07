package org.example.smartinventory.service;

import org.example.smartinventory.entities.ContactEntity;
import org.example.smartinventory.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Override
    public Optional<ContactEntity> createContact(ContactEntity contact) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactEntity> getContactByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<ContactEntity> getContactsByName(String name) {
        return List.of();
    }

    @Override
    public Optional<ContactEntity> updateContact(ContactEntity contact) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactEntity> updateContactEmail(int contactId, String newEmail) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactEntity> updateContactPhone(int contactId, String newPhone) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactEntity> updateContactAddress(int contactId, String newAddress) {
        return Optional.empty();
    }

    @Override
    public boolean deleteContact(int contactId) {
        return false;
    }

    @Override
    public void deleteAllContacts() {

    }

    @Override
    public List<ContactEntity> searchContacts(String keyword) {
        return List.of();
    }

    @Override
    public long getContactCount() {
        return 0;
    }

    @Override
    public boolean isEmailUnique(String email) {
        return false;
    }

    @Override
    public boolean isPhoneUnique(String phone) {
        return false;
    }

    @Override
    public Page<ContactEntity> getContactsPaginated(int page, int size) {
        return null;
    }

    @Override
    public List<ContactEntity> getAllContactsSorted(String sortBy, String sortOrder) {
        return List.of();
    }

    @Override
    public List<Optional<ContactEntity>> createContacts(List<ContactEntity> contacts) {
        return List.of();
    }

    @Override
    public int deleteContacts(List<Integer> contactIds) {
        return 0;
    }

    @Override
    public byte[] exportContactsToCsv() {
        return new byte[0];
    }

    @Override
    public List<Optional<ContactEntity>> importContactsFromCsv(byte[] csvData) {
        return List.of();
    }
}
