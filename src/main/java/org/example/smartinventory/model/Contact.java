package org.example.smartinventory.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contact {

    private int Id;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private Address contactAddress;

    public Contact(int id, String contactName, String contactEmail, String contactPhone, Address contactAddress) {
        Id = id;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
    }
}
