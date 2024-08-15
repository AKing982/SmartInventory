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
    private String role;
    private String confirmPassword;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String company;
    private String address;
    private boolean termsAgreed;
    private String jobTitle;
    private LocalDate hireDate;

    public Registration(String firstName, String lastName, String email, String username, String password, String role, String company, String jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.company = company;
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", termsAgreed=" + termsAgreed +
                ", jobTitle='" + jobTitle + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}
