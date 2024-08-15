package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Employee;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.EmployeeService;
import org.example.smartinventory.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static org.example.smartinventory.workbench.registration.utilities.EmployeeRegistrationUtil.createEmployee;
import static org.example.smartinventory.workbench.registration.utilities.EmployeeRegistrationUtil.createEmployeeEntity;

@Component
public class EmployeeRegistration implements RegistrationStrategy<EmployeeEntity>
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
        if(registration == null || permissionsService == null){
            throw new IllegalArgumentException("Registration is null");
        }
        validateRegistrationParams(registration);
//        String email = registration.getEmail();
//        Optional<UserEntity> userByEmail = Optional.of(userRepository.findByEmail(email)
//                .orElseThrow());
//
//        UserEntity user = userByEmail.get();
//        Employee employee = createEmployee(registration);
//        EmployeeEntity employeeEntity = createEmployeeEntity(employee, user);
//        employeeService.save(employeeEntity);
//        // Create the Employee Entity
//        // Save the employee entity and return
//        return Optional.of(employeeEntity);
        return null;
    }

    public void validateRegistrationParams(Registration registration) {
        String email = registration.getEmail();
        String password = registration.getPassword();
        String firstName = registration.getFirstName();
        String lastName = registration.getLastName();
        String jobTitle = registration.getJobTitle();
        String username = registration.getUsername();
        String role = registration.getRole();
        if(email.isEmpty()){
            throw new IllegalArgumentException("Email is null or empty");
        }
        else if(password.isEmpty()){
            throw new IllegalArgumentException("Password is null or empty");
        }
        else if(firstName.isEmpty()){
            throw new IllegalArgumentException("First name is null or empty");
        }
        else if(lastName.isEmpty()){
            throw new IllegalArgumentException("Last name is null or empty");
        }
        else if(role.isEmpty()){
            throw new IllegalArgumentException("Role is null or empty");
        }
        else if(jobTitle.isEmpty()){
            throw new IllegalArgumentException("Job title is null or empty");
        }
        else if( username.isEmpty()){
            throw new IllegalArgumentException("Username is null or empty");
        }else{
            throw new NullPointerException("Parameter is null");
        }
    }

    private void validateField(String value, String fieldName){
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException("Value is null or empty");
        }
    }

    public Set<Permission> getPermissions(EmployeeEntity employeeEntity, PermissionsService permissionsService) {
        UserEntity userEntity = employeeEntity.getUser();
        return permissionsService.getPermissionsForUser(userEntity);
    }


}
