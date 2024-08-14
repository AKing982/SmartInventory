package org.example.smartinventory.workbench.converter;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.model.Registration;

public class RegistrationConverter implements Converter<RegistrationDTO, Registration>
{

    @Override
    public Registration convert(RegistrationDTO registrationDTO)
    {
        Registration registration = new Registration();
        registration.setEmail(registration.getEmail());
        registration.setPassword(registration.getPassword());
        registration.setFirstName(registration.getFirstName());
        registration.setLastName(registration.getLastName());
        registration.setJobTitle(registration.getJobTitle());
        registration.setTermsAgreed(registration.isTermsAgreed());
        registration.setUsername(registration.getUsername());
        registration.setCompany(registration.getCompany());
        return registration;
    }
}
