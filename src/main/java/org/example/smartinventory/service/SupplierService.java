package org.example.smartinventory.service;

import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.model.Registration;

public interface SupplierService extends ServiceModel<SupplierEntity>
{
    SupplierEntity createSupplierFromRegistration(Registration registration);
}
