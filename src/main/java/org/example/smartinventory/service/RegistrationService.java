package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService
{
    private UserService userService;
    private EmployeeService employeeService;
    private ManagerService managerService;
    private RoleService roleService;

    @Autowired
    public RegistrationService(UserService userService,
                               EmployeeService employeeService,
                               ManagerService managerService,
                               RoleService roleService)
    {
        this.userService = userService;
        this.employeeService = employeeService;
        this.managerService = managerService;
        this.roleService = roleService;
    }

    public Optional<UserEntity> registerRegularUser(RegistrationDTO registrationDTO)
    {
        return null;
    }

    public void registerUser(RegistrationDTO registrationDTO)
    {
        userService.createUserFromRegistration(registrationDTO);
    }

    public Optional<ManagerEntity> registerManager(RegistrationDTO registrationDTO)
    {
        return null;
    }

    public Optional<EmployeeEntity> registerEmployee(RegistrationDTO registrationDTO){
        return null;
    }

    public Optional<RoleEntity> registerRole(RegistrationDTO registrationDTO){
        return null;
    }

    public Optional<SupplierEntity> registerSupplier(RegistrationDTO registrationDTO){
        return null;
    }

}
