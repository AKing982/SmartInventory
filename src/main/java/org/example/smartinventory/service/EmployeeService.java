package org.example.smartinventory.service;

import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.EmployeeRoleAssignment;
import org.example.smartinventory.entities.ManagerEntity;
import org.example.smartinventory.model.Employee;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService extends ServiceModel<EmployeeEntity>
{

    List<EmployeeEntity> findByWarehouseId(int warehouseId);

    List<EmployeeEntity> findByJobTitle(String jobTitle);

    List<EmployeeEntity> findByRole(EmployeeRole role);

    List<EmployeeEntity> findByActiveStatus(boolean isActive);

    Optional<EmployeeEntity> findByUsername(String username);

    List<EmployeeEntity> searchEmployees(String keyword);

    int updateEmployeeActiveStatus(int employeeId, boolean isActive);

    int updateEmployeeWarehouse(int employeeId, int warehouseId);

    void createEmployee(EmployeeEntity employee);

    int updateEmployee(int id, EmployeeEntity updatedEmployee);

    Page<EmployeeEntity> findPaginated(int page, int size);

    boolean isUsernameUnique(String username);

    Map<String, Long> getEmployeeCountByWarehouse();

    boolean assignEmployeeToInventoryTask(int employeeId, int inventoryTaskId);

    List<EmployeeEntity> findAvailableEmployeesForInventoryCount();

    boolean updateEmployeeInventoryPermissions(int employeeId, Set<Permission> permissions);

    boolean logInventoryActivity(int employeeId, String activityType, String description);

    Optional<ManagerEntity> getWarehouseManager(int warehouseId);

    boolean assignWarehouseManager(int employeeId, int warehouseId);
}
