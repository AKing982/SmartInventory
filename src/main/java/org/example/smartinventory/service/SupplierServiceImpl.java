package org.example.smartinventory.service;

import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.SupplierRepository;
import org.example.smartinventory.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService
{
    private SupplierRepository supplierRepository;
    private UserRepository userRepository;
    private Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               UserRepository userRepository)
    {
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<SupplierEntity> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public void save(SupplierEntity supplierEntity) {
        supplierRepository.save(supplierEntity);
    }

    @Override
    public void delete(SupplierEntity supplierEntity) {
        supplierRepository.delete(supplierEntity);
    }

    @Override
    public Optional<SupplierEntity> findById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Collection<SupplierEntity> findAllById(Iterable<Long> ids) {
        return supplierRepository.findAllById(ids);
    }

    @Override
    public SupplierEntity createSupplierFromRegistration(Registration registration) {
        SupplierEntity supplierEntity = new SupplierEntity();
        try
        {
            supplierEntity.setSupplierName(registration.getCompany());
            supplierEntity.setEmployeeRole(EmployeeRole.ROLE_SUPPLIER);

            UserEntity userEntity = userRepository.findByEmail(registration.getEmail())
                    .orElseThrow();
            supplierEntity.setUser(userEntity);

        }catch(Exception e)
        {
            LOGGER.error("Unable to create Supplier from registration: ", e);
            throw e;
        }
        return supplierEntity;
    }
}
