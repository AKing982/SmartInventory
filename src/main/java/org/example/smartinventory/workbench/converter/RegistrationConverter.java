package org.example.smartinventory.workbench.converter;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.model.Registration;

public class RegistrationConverter implements Converter<RegistrationDTO, Registration>
{

    @Override
    public Registration convert(RegistrationDTO registrationDTO)
    {
        Registration registration = new Registration();
        registration.setEmail(registrationDTO.email());
        registration.setPassword(registrationDTO.password());
        registration.setFirstName(registrationDTO.firstName());
        registration.setLastName(registrationDTO.lastName());
        registration.setJobTitle(registrationDTO.jobTitle());
        registration.setUsername(registrationDTO.username());
        registration.setCompany(registrationDTO.companyName());
        registration.setRole(registrationDTO.role().name());
        return registration;
    }
}
