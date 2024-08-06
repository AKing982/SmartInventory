package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="contacts")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;

    private String contactName;

    private String contactEmail;

    private String contactPhone;

    private String contactAddress;

    public ContactEntity(int contactId, String contactName, String contactEmail, String contactPhone, String contactAddress) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
    }
}
