package org.example.smartinventory.workbench.registration.utilities;

import org.example.smartinventory.dto.RegistrationDTO;
import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Employee;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Registration;

import java.time.LocalDateTime;

public class EmployeeRegistrationUtil
{
    public static Employee createEmployee(Registration registrationDTO){
        Employee employee = new Employee();
        employee.setTitle(registrationDTO.getJobTitle());
        employee.setEmployeeRole(registrationDTO.getRole());
        employee.setDepartment(null);
        employee.setCompany(registrationDTO.getCompany());
        employee.setUser(null);
        employee.setEmpFirstName(registrationDTO.getFirstName());
        employee.setEmpLastName(registrationDTO.getLastName());
        employee.setEmpEmail(registrationDTO.getEmail());
        return employee;
    }

    public static EmployeeEntity createEmployeeEntity(Employee employee, UserEntity user) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setUser(user);
        employeeEntity.setRole(getEmployeeRoleFromValue(employee.getEmployeeRole()));
        employeeEntity.setDepartment(null);
        employeeEntity.setJobTitle(employee.getTitle());
        employeeEntity.setCreatedAt(LocalDateTime.now());
        employeeEntity.setWarehouse(null);
        return employeeEntity;
    }

    public static EmployeeRole getEmployeeRoleFromValue(String role){
        return EmployeeRole.valueOf(role);
    }
}
