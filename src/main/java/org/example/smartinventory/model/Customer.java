package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Customer {

    private int CustomerID;
    private String CustomerName;
    private String CustomerEmail;
    private String CustomerPhone;
    private Address customerAddress;
    private LocalDate dateAdded;

    public Customer(int customerID, String customerName, String customerEmail, String customerPhone, Address customerAddress, LocalDate dateAdded) {
        CustomerID = customerID;
        CustomerName = customerName;
        CustomerEmail = customerEmail;
        CustomerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.dateAdded = dateAdded;
    }


}
