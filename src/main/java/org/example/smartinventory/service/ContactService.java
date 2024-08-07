package org.example.smartinventory.service;

import org.example.smartinventory.entities.ContactEntity;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface ContactService extends ServiceModel<ContactEntity>
{
    // Create
    Optional<ContactEntity> createContact(ContactEntity contact);

    // Read
    Optional<ContactEntity> getContactByEmail(String email);
    List<ContactEntity> getContactsByName(String name);

    // Update
    Optional<ContactEntity> updateContact(ContactEntity contact);
    Optional<ContactEntity> updateContactEmail(int contactId, String newEmail);
    Optional<ContactEntity> updateContactPhone(int contactId, String newPhone);
    Optional<ContactEntity> updateContactAddress(int contactId, String newAddress);

    // Delete
    boolean deleteContact(int contactId);
    void deleteAllContacts();

    // Search
    List<ContactEntity> searchContacts(String keyword);

    // Count
    long getContactCount();

    // Validation
    boolean isEmailUnique(String email);
    boolean isPhoneUnique(String phone);

    // Pagination
    Page<ContactEntity> getContactsPaginated(int page, int size);

    // Sorting
    List<ContactEntity> getAllContactsSorted(String sortBy, String sortOrder);

    // Bulk operations
    List<Optional<ContactEntity>> createContacts(List<ContactEntity> contacts);
    int deleteContacts(List<Integer> contactIds);

    // Export
    byte[] exportContactsToCsv();

    // Import
    List<Optional<ContactEntity>> importContactsFromCsv(byte[] csvData);
}
