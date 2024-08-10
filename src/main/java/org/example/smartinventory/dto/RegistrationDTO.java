package org.example.smartinventory.dto;

public record RegistrationDTO(String firstName, String lastName, String email, String username,
                              String password, String jobTitle, String role, String company) {

}
