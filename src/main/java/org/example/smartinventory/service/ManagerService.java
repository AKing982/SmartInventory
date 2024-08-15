package org.example.smartinventory.service;

import org.example.smartinventory.entities.ManagerEntity;

public interface ManagerService extends ServiceModel<ManagerEntity>
{
    ManagerEntity createManagerFromRegistration(RegistrationService registrationService);
}
