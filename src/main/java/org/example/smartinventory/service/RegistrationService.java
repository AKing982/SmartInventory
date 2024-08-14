package org.example.smartinventory.service;

import org.example.smartinventory.dto.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void registerUser(RegistrationDTO registrationDTO)
    {
        userService.createUserFromRegistration(registrationDTO);
    }

}
