package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Employee;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.EmployeeService;
import org.example.smartinventory.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.example.smartinventory.workbench.registration.utilities.EmployeeRegistrationUtil.createEmployee;
import static org.example.smartinventory.workbench.registration.utilities.EmployeeRegistrationUtil.createEmployeeEntity;

@Component
public class EmployeeRegistration implements RegistrationStrategy
{
    private EmployeeService employeeService;
    private UserRepository userRepository;

    @Autowired
    public EmployeeRegistration(EmployeeService employeeService,
                                UserRepository userRepository){
        this.employeeService = employeeService;
        this.userRepository = userRepository;
    }

    @Override
    public String getRole() {
        return "ROLE_EMPLOYEE";
    }

    @Override
    public Optional<EmployeeEntity> register(Registration registration, PermissionsService permissionsService) {

        String email = registration.getEmail();
        Optional<UserEntity> userByEmail = Optional.of(userRepository.findByEmail(email)
                .orElseThrow());

        UserEntity user = userByEmail.get();
        Employee employee = createEmployee(registration);
        EmployeeEntity employeeEntity = createEmployeeEntity(employee, user);
        employeeService.save(employeeEntity);
        // Create the Employee Entity
        // Save the employee entity and return
        return Optional.of(employeeEntity);
    }


}
