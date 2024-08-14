package org.example.smartinventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee
{
    private String empFirstName;
    private String empLastName;
    private String empEmail;
    private String empPhone;
    private String title;
    private User user;
    private String department;
    private String company;
    private String employeeRole;
    private Collection<Department> departmentCollection;
}
