package org.example.smartinventory.repository;

import org.example.smartinventory.entities.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long>
{
    // Filtering
    @Query("SELECT c FROM ContactEntity c WHERE c.contactName LIKE %:name%")
    List<ContactEntity> findByNameContaining(@Param("name") String name);

    @Query("SELECT c FROM ContactEntity c WHERE c.contactEmail = :email")
    Optional<ContactEntity> findByEmail(@Param("email") String email);

    @Query("SELECT c FROM ContactEntity c WHERE c.contactPhone = :phone")
    List<ContactEntity> findByPhone(@Param("phone") String phone);

    // Advanced queries
    @Query("SELECT c FROM ContactEntity c WHERE c.contactName LIKE %:keyword% OR c.contactEmail LIKE %:keyword% OR c.contactPhone LIKE %:keyword% OR c.contactAddress LIKE %:keyword%")
    List<ContactEntity> searchContacts(@Param("keyword") String keyword);

    @Query("SELECT COUNT(c) FROM ContactEntity c")
    long getContactCount();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ContactEntity c WHERE c.contactEmail = :email")
    boolean existsByEmail(@Param("email") String email);

    // Partial updates
    @Modifying
    @Query("UPDATE ContactEntity c SET c.contactEmail = :newEmail WHERE c.contactId = :id")
    int updateContactEmail(@Param("id") int id, @Param("newEmail") String newEmail);

    @Modifying
    @Query("UPDATE ContactEntity c SET c.contactPhone = :newPhone WHERE c.contactId = :id")
    int updateContactPhone(@Param("id") int id, @Param("newPhone") String newPhone);

    @Modifying
    @Query("UPDATE ContactEntity c SET c.contactAddress = :newAddress WHERE c.contactId = :id")
    int updateContactAddress(@Param("id") int id, @Param("newAddress") String newAddress);

    // Custom business logic
    @Query("SELECT c FROM ContactEntity c ORDER BY c.contactId DESC")
    List<ContactEntity> findRecentContacts(Pageable pageable);

    // Bulk operations
    @Modifying
    @Query("UPDATE ContactEntity c SET c.contactAddress = :newAddress WHERE c.contactAddress = :oldAddress")
    int bulkUpdateContactAddress(@Param("oldAddress") String oldAddress, @Param("newAddress") String newAddress);

    // Statistics
    @Query("SELECT SUBSTRING(c.contactEmail, LOCATE('@', c.contactEmail) + 1) AS domain, COUNT(c) AS count FROM ContactEntity c GROUP BY domain")
    List<Object[]> getContactCountByDomain();

    // Sorting
    @Query("SELECT c FROM ContactEntity c ORDER BY " +
            "CASE WHEN :sortBy = 'contactName' AND :direction = 0 THEN c.contactName END ASC, " +
            "CASE WHEN :sortBy = 'contactName' AND :direction = 1 THEN c.contactName END DESC, " +
            "CASE WHEN :sortBy = 'contactEmail' AND :direction = 0 THEN c.contactEmail END ASC, " +
            "CASE WHEN :sortBy = 'contactEmail' AND :direction = 1 THEN c.contactEmail END DESC, " +
            "CASE WHEN :sortBy = 'contactPhone' AND :direction = 0 THEN c.contactPhone END ASC, " +
            "CASE WHEN :sortBy = 'contactPhone' AND :direction = 1 THEN c.contactPhone END DESC, " +
            "CASE WHEN :sortBy = 'contactAddress' AND :direction = 0 THEN c.contactAddress END ASC, " +
            "CASE WHEN :sortBy = 'contactAddress' AND :direction = 1 THEN c.contactAddress END DESC")
    List<ContactEntity> findAllSorted(@Param("sortBy") String sortBy, @Param("direction") int direction);
}
