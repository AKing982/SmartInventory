package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name="customers")
@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private String customerAddress;

    private LocalDate dateAdded;

    public CustomerEntity(int customerId, String customerName, String customerEmail, String customerPhone, String customerAddress, LocalDate dateAdded) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.dateAdded = dateAdded;
    }
}
