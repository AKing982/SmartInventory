package org.example.smartinventory.repository;

import org.checkerframework.checker.units.qual.A;
import org.example.smartinventory.entities.ContactEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ContactRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11-alpine")
            .withDatabaseName("test")
            .withUsername("alpine")
            .withPassword("alpine");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ContactEntity testContact;

    @BeforeEach
    void setUp() {
        testContact = new ContactEntity(0, "John Doe", "john@example.com", "1234567890", "123 Test St");
        entityManager.persist(testContact);
        entityManager.flush();

    }

    @Test
    void testFindByNameContaining() {
        List<ContactEntity> found = contactRepository.findByNameContaining("John");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getContactName()).isEqualTo("John Doe");
    }

    @Test
    void testFindByEmail() {
        Optional<ContactEntity> found = contactRepository.findByEmail("john@example.com");
        assertThat(found).isPresent();
        assertThat(found.get().getContactEmail()).isEqualTo("john@example.com");
    }

    @Test
    void testFindByPhone() {
        List<ContactEntity> found = contactRepository.findByPhone("1234567890");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getContactPhone()).isEqualTo("1234567890");
    }

    @Test
    void testSearchContacts() {
        List<ContactEntity> found = contactRepository.searchContacts("John");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getContactName()).isEqualTo("John Doe");

        found = contactRepository.searchContacts("Test St");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getContactAddress()).isEqualTo("123 Test St");
    }

    @Test
    void testGetContactCount() {
        long count = contactRepository.getContactCount();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testExistsByEmail() {
        boolean exists = contactRepository.existsByEmail("john@example.com");
        assertThat(exists).isTrue();

        exists = contactRepository.existsByEmail("nonexistent@example.com");
        assertThat(exists).isFalse();
    }

    @Test
    void testUpdateContactEmail() {
        int updated = contactRepository.updateContactEmail(testContact.getContactId(), "newemail@example.com");
        assertThat(updated).isEqualTo(1);

        ContactEntity updatedContact = entityManager.find(ContactEntity.class, testContact.getContactId());
        assertThat(updatedContact.getContactEmail()).isEqualTo("newemail@example.com");
    }

    @Test
    void testUpdateContactPhone() {
        int updated = contactRepository.updateContactPhone(testContact.getContactId(), "9876543210");
        assertThat(updated).isEqualTo(1);

        ContactEntity updatedContact = entityManager.find(ContactEntity.class, testContact.getContactId());
        assertThat(updatedContact.getContactPhone()).isEqualTo("9876543210");
    }

    @Test
    void testUpdateContactAddress() {
        int updated = contactRepository.updateContactAddress(testContact.getContactId(), "456 New St");
        assertThat(updated).isEqualTo(1);

        ContactEntity updatedContact = entityManager.find(ContactEntity.class, testContact.getContactId());
        assertThat(updatedContact.getContactAddress()).isEqualTo("456 New St");
    }

    @Test
    void testFindRecentContacts() {
        ContactEntity newContact = new ContactEntity(0, "Jane Doe", "jane@example.com", "9876543210", "456 Test St");
        entityManager.persist(newContact);
        entityManager.flush();

        List<ContactEntity> recentContacts = contactRepository.findRecentContacts(PageRequest.of(0, 2));
        assertThat(recentContacts).hasSize(2);
        assertThat(recentContacts.get(0).getContactName()).isEqualTo("Jane Doe");
    }

    @Test
    void testBulkUpdateContactAddress() {
        ContactEntity contact2 = new ContactEntity(0, "Jane Doe", "jane@example.com", "9876543210", "123 Test St");
        entityManager.persist(contact2);
        entityManager.flush();

        int updated = contactRepository.bulkUpdateContactAddress("123 Test St", "789 New St");
        assertThat(updated).isEqualTo(2);

        List<ContactEntity> updatedContacts = contactRepository.findAll();
        assertThat(updatedContacts).allMatch(c -> c.getContactAddress().equals("789 New St"));
    }

    @Test
    void testGetContactCountByDomain() {
        ContactEntity contact2 = new ContactEntity(0, "Jane Doe", "jane@gmail.com", "9876543210", "456 Test St");
        entityManager.persist(contact2);
        entityManager.flush();

        List<Object[]> countByDomain = contactRepository.getContactCountByDomain();
        assertThat(countByDomain).hasSize(2);
        Map<String, Long> countMap = countByDomain.stream()
                .collect(Collectors.toMap(arr -> (String) arr[0], arr -> (Long) arr[1]));
        assertThat(countMap).containsEntry("example.com", 1L);
        assertThat(countMap).containsEntry("gmail.com", 1L);
    }

    @Test
    void testFindAllSorted() {
        ContactEntity contact2 = new ContactEntity(0, "Alice Smith", "alice@example.com", "5555555555", "789 Test St");
        entityManager.persist(contact2);
        entityManager.flush();

        List<ContactEntity> sortedContacts = contactRepository.findAllSorted("contactName", 0); // ASC
        assertThat(sortedContacts).hasSize(2);
        assertThat(sortedContacts.get(0).getContactName()).isEqualTo("Alice Smith");
        assertThat(sortedContacts.get(1).getContactName()).isEqualTo("John Doe");

        sortedContacts = contactRepository.findAllSorted("contactName", 1); // DESC
        assertThat(sortedContacts).hasSize(2);
        assertThat(sortedContacts.get(0).getContactName()).isEqualTo("John Doe");
        assertThat(sortedContacts.get(1).getContactName()).isEqualTo("Alice Smith");
    }

    @Test
    void testFindByNameContainingNoResults() {
        List<ContactEntity> found = contactRepository.findByNameContaining("Nonexistent");
        assertThat(found).isEmpty();
    }

    @Test
    void testFindByEmailNonexistent() {
        Optional<ContactEntity> found = contactRepository.findByEmail("nonexistent@example.com");
        assertThat(found).isNotPresent();
    }

    @Test
    void testSearchContactsMultipleResults() {
        ContactEntity contact2 = new ContactEntity(0, "John Smith", "johnsmith@example.com", "9876543210", "456 Test St");
        entityManager.persist(contact2);
        entityManager.flush();

        List<ContactEntity> found = contactRepository.searchContacts("John");
        assertThat(found).hasSize(2);
        assertThat(found).extracting(ContactEntity::getContactName)
                .containsExactlyInAnyOrder("John Doe", "John Smith");
    }

    @Test
    void testUpdateContactEmailNonexistent() {
        int updated = contactRepository.updateContactEmail(9999, "newemail@example.com");
        assertThat(updated).isZero();
    }

    @Test
    void testBulkUpdateContactAddressNoMatch() {
        int updated = contactRepository.bulkUpdateContactAddress("Nonexistent St", "New St");
        assertThat(updated).isZero();
    }

    @Test
    void testFindRecentContactsWithLimit() {
        for (int i = 0; i < 10; i++) {
            ContactEntity contact = new ContactEntity(0, "Contact " + i, "contact" + i + "@example.com", "123456789" + i, i + " Test St");
            entityManager.persist(contact);
        }
        entityManager.flush();

        List<ContactEntity> recentContacts = contactRepository.findRecentContacts(PageRequest.of(0, 5));
        assertThat(recentContacts).hasSize(5);
        assertThat(recentContacts.get(0).getContactName()).isEqualTo("Contact 9");
        assertThat(recentContacts.get(4).getContactName()).isEqualTo("Contact 5");
    }

    @Test
    void testGetContactCountByDomainNoContacts() {
        contactRepository.deleteAll();
        entityManager.flush();

        List<Object[]> countByDomain = contactRepository.getContactCountByDomain();
        assertThat(countByDomain).isEmpty();
    }

    @Test
    void testFindAllSortedWithNullValues() {
        ContactEntity contact2 = new ContactEntity(0, null, "noemail@example.com", null, "789 Test St");
        entityManager.persist(contact2);
        entityManager.flush();

        List<ContactEntity> sortedContacts = contactRepository.findAllSorted("contactName", 0); // ASC
        assertThat(sortedContacts).hasSize(2);
        assertThat(sortedContacts.get(0).getContactName()).isNull();
        assertThat(sortedContacts.get(1).getContactName()).isEqualTo("John Doe");

        sortedContacts = contactRepository.findAllSorted("contactPhone", 0); // ASC
        assertThat(sortedContacts).hasSize(2);
        assertThat(sortedContacts.get(0).getContactPhone()).isNull();
        assertThat(sortedContacts.get(1).getContactPhone()).isEqualTo("1234567890");
    }

    @Test
    void testFindAllSortedInvalidField() {
        assertThatThrownBy(() -> contactRepository.findAllSorted("invalidField", 0))
                .isInstanceOf(Exception.class);
    }


    @AfterEach
    void tearDown() {
        entityManager.clear();
    }
}