package org.example.smartinventory.dto;

import org.example.smartinventory.model.RoleType;

public record RegistrationDTO(String firstName, String lastName, String email, String username,
                              String password, String jobTitle, RoleType role, String companyName) {

}
