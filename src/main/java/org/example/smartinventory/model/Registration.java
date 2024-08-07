package org.example.smartinventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration
{
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private boolean termsAgreed;
    private String jobTitle;
    private LocalDate hireDate;
}
