package org.example.smartinventory.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@Builder
public class Department
{
    private int departmentId;
    private String departmentName;
    private String departmentDescription;
    private int managerId;
    private String managerName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isActive;

    private Collection<Employee> employees;
    private Address address;

    public Department(int departmentId, String departmentName, String departmentDescription, int managerId, String managerName, LocalDateTime createDate, LocalDateTime updateDate, boolean isActive, Collection<Employee> employees, Address address) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.managerId = managerId;
        this.managerName = managerName;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.isActive = isActive;
        this.employees = employees;
        this.address = address;
    }
}
