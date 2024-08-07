package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class Employee
{
    private String empFirstName;
    private String empLastName;
    private String empEmail;
    private String empPhone;
    private EmployeeRole employeeRole;
    private Collection<Department> departmentCollection;
}
