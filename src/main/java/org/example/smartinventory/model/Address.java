package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address
{
    private String street;
    private String country;
    private String state;
    private String city;
    private String ZIP;

    public Address(String street, String country, String state, String city, String ZIP) {
        this.street = street;
        this.country = country;
        this.state = state;
        this.city = city;
        this.ZIP = ZIP;
    }


}
