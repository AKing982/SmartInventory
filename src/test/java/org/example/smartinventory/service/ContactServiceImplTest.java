package org.example.smartinventory.service;

import org.example.smartinventory.entities.ContactEntity;
import org.example.smartinventory.repository.ContactRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyIterable;


@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    private ContactEntity testContact;

    @BeforeEach
    void setUp() {
        testContact = new ContactEntity(0, "John Doe", "john@example.com", "1234567890", "123 Test St");
    }


    @Test
    void testFindAll() {
        List<ContactEntity> contacts = Arrays.asList(testContact);
        when(contactRepository.findAll()).thenReturn(contacts);

        Collection<ContactEntity> result = contactService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result).contains(testContact);
    }

    @Test
    void testSave() {
        contactService.save(testContact);

        verify(contactRepository).save(testContact);
    }

    @Test
    void testDelete() {
        contactService.delete(testContact);

        verify(contactRepository).delete(testContact);
    }

    @Test
    void testFindById() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(testContact));

        Optional<ContactEntity> result = contactService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testContact);
    }

    @Test
    void testFindAllById() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<ContactEntity> contacts = Arrays.asList(testContact);
        when(contactRepository.findAllById(ids)).thenReturn(contacts);

        Collection<ContactEntity> result = contactService.findAllById(ids);

        assertThat(result).hasSize(1);
        assertThat(result).contains(testContact);
    }

    @Test
    void testCreateContact() {
        when(contactRepository.save(testContact)).thenReturn(testContact);

        Optional<ContactEntity> result = contactService.createContact(testContact);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testContact);
    }

    @Test
    void testGetContactByEmail() {
        when(contactRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testContact));

        Optional<ContactEntity> result = contactService.getContactByEmail("john@example.com");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testContact);
    }

    @Test
    void testGetContactsByName() {
        when(contactRepository.findByNameContaining("John")).thenReturn(Arrays.asList(testContact));

        List<ContactEntity> result = contactService.getContactsByName("John");

        assertThat(result).hasSize(1);
        assertThat(result).contains(testContact);
    }

    @Test
    void testUpdateContact() {
        when(contactRepository.save(testContact)).thenReturn(testContact);

        Optional<ContactEntity> result = contactService.updateContact(testContact);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testContact);
    }

    @Test
    void testUpdateContactEmail() {
        when(contactRepository.updateContactEmail(1, "newemail@example.com")).thenReturn(1);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(testContact));

        Optional<ContactEntity> result = contactService.updateContactEmail(1, "newemail@example.com");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testContact);
    }

    @Test
    void testUpdateContactPhone() {
        when(contactRepository.updateContactPhone(1, "9876543210")).thenReturn(1);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(testContact));

        Optional<ContactEntity> result = contactService.updateContactPhone(1, "9876543210");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testContact);
    }

    @Test
    void testUpdateContactAddress() {
        when(contactRepository.updateContactAddress(1, "456 New St")).thenReturn(1);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(testContact));

        Optional<ContactEntity> result = contactService.updateContactAddress(1, "456 New St");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testContact);
    }

    @Test
    void testDeleteContact() {
        when(contactRepository.existsById(1L)).thenReturn(true);

        boolean result = contactService.deleteContact(1);

        assertThat(result).isTrue();
        verify(contactRepository).deleteById(1L);
    }

    @Test
    void testDeleteAllContacts() {
        contactService.deleteAllContacts();

        verify(contactRepository).deleteAll();
    }

    @Test
    void testSearchContacts() {
        when(contactRepository.searchContacts("John")).thenReturn(Arrays.asList(testContact));

        List<ContactEntity> result = contactService.searchContacts("John");

        assertThat(result).hasSize(1);
        assertThat(result).contains(testContact);
    }

    @Test
    void testGetContactCount() {
        when(contactRepository.getContactCount()).thenReturn(5L);

        long result = contactService.getContactCount();

        assertThat(result).isEqualTo(5L);
    }

    @Test
    void testIsEmailUnique() {
        when(contactRepository.existsByEmail("john@example.com")).thenReturn(false);

        boolean result = contactService.isEmailUnique("john@example.com");

        assertThat(result).isTrue();
    }

    @Test
    void testIsPhoneUnique() {
        when(contactRepository.findByPhone("1234567890")).thenReturn(Collections.emptyList());

        boolean result = contactService.isPhoneUnique("1234567890");

        assertThat(result).isTrue();
    }

    @Test
    void testGetContactsPaginated() {
        Page<ContactEntity> page = new PageImpl<>(Arrays.asList(testContact));
        when(contactRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ContactEntity> result = contactService.getContactsPaginated(0, 10);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).contains(testContact);
    }

    @Test
    void testGetAllContactsSorted() {
        when(contactRepository.findAllSorted("contactName", 0)).thenReturn(Arrays.asList(testContact));

        List<ContactEntity> result = contactService.getAllContactsSorted("contactName", "ASC");

        assertThat(result).hasSize(1);
        assertThat(result).contains(testContact);
    }

    @Test
    void testCreateContacts() {
        List<ContactEntity> contacts = Arrays.asList(testContact);
        when(contactRepository.saveAll(contacts)).thenReturn(contacts);

        List<Optional<ContactEntity>> result = contactService.createContacts(contacts);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isPresent();
        assertThat(result.get(0).get()).isEqualTo(testContact);
    }

    @Test
    void testDeleteContacts() {
        List<Integer> ids = Arrays.asList(1, 2);

        // Mock the behavior of existsById for each ID
        when(contactRepository.existsById(1L)).thenReturn(true);
        when(contactRepository.existsById(2L)).thenReturn(true);

        int result = contactService.deleteContacts(ids);

        assertThat(result).isEqualTo(2);

        // Verify that deleteById was called for each ID
        verify(contactRepository).deleteById(1L);
        verify(contactRepository).deleteById(2L);
    }

    @Test
    void testExportContactsToCsv() {
        List<ContactEntity> contacts = Arrays.asList(testContact);
        when(contactRepository.findAll()).thenReturn(contacts);

        byte[] result = contactService.exportContactsToCsv();

        assertThat(result).isNotEmpty();
        // You might want to add more specific assertions here to check the CSV content
    }

    @Test
    void testImportContactsFromCsv() {
        String csvData = "John Doe,john@example.com,1234567890,123 Test St";
        when(contactRepository.saveAll(any())).thenReturn(Arrays.asList(testContact));

        List<Optional<ContactEntity>> result = contactService.importContactsFromCsv(csvData.getBytes());

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isPresent();
        assertThat(result.get(0).get()).isEqualTo(testContact);
    }


    @AfterEach
    void tearDown() {
    }
}