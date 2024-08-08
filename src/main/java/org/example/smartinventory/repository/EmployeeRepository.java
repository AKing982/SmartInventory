package org.example.smartinventory.repository;

import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.EmployeeRoleAssignment;
import org.example.smartinventory.entities.ManagerEntity;
import org.example.smartinventory.model.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer>
{
    @Query("SELECT e FROM EmployeeEntity e WHERE e.firstName = :firstName AND e.lastName = :lastName")
    List<EmployeeEntity> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.email =:email")
    Optional<EmployeeEntity> findByEmail(@Param("email") String email);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.username =:username")
    Optional<EmployeeEntity> findByUsername(@Param("username") String username);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.department.departmentId =:departmentId")
    List<EmployeeEntity> findByDepartmentId(@Param("departmentId") int departmentId);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.warehouse.warehouseId =:warehouseId")
    List<EmployeeEntity> findByWarehouseId(@Param("warehouseId") int warehouseId);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.jobTitle =:jobTitle")
    List<EmployeeEntity> findByJobTitle(@Param("jobTitle") String jobTitle);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.role =:role")
    List<EmployeeEntity> findByRole(@Param("role") EmployeeRole role);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.isActive =:isActive")
    List<EmployeeEntity> findByActiveStatus(@Param("isActive") boolean isActive);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.hireDate >=:startDate AND e.hireDate <=:endDate")
    List<EmployeeEntity> findByHireDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.managerEntity.managerId =:managerId")
    List<EmployeeEntity> findByManagerId(@Param("managerId") int managerId);

    @Query("SELECT e FROM EmployeeEntity e WHERE :roleAssignment MEMBER OF e.roleAssignments")
    List<EmployeeEntity> findByRoleAssignment(@Param("roleAssignment") EmployeeRoleAssignment roleAssignment);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.salary >:minSalary")
    List<EmployeeEntity> findBySalaryGreaterThan(@Param("minSalary") String minSalary);

    @Query("SELECT e FROM EmployeeEntity e ORDER BY e.hireDate DESC")
    List<EmployeeEntity> findAllOrderByHireDateDesc();

    @Query("SELECT COUNT(e) FROM EmployeeEntity e WHERE e.department.departmentId =:departmentId")
    long countEmployeesByDepartment(@Param("departmentId") int departmentId);

    @Query("SELECT e FROM EmployeeEntity e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EmployeeEntity> searchEmployees(@Param("keyword") String keyword);

    @Query("SELECT w.managerEntity FROM EmployeeEntity e JOIN e.warehouse w WHERE w.warehouseId =:id")
    Optional<ManagerEntity> findWarehouseManagerById(@Param("id") int id);

    @Modifying
    @Query("UPDATE EmployeeEntity e SET e.isActive =:isActive WHERE e.employeeId =:employeeId")
    int updateEmployeeActiveStatus(@Param("employeeId") int employeeId, @Param("isActive") boolean isActive);

    @Modifying
    @Query("UPDATE EmployeeEntity e SET e.department.departmentId =:departmentId WHERE e.employeeId =:employeeId")
    int updateEmployeeDepartment(@Param("employeeId") int employeeId, @Param("departmentId") int departmentId);

    @Modifying
    @Query("UPDATE EmployeeEntity e SET e.salary = :newSalary WHERE e.employeeId = :employeeId")
    int updateEmployeeSalary(@Param("employeeId") int employeeId, @Param("newSalary") String newSalary);

    @Query("SELECT e FROM EmployeeEntity e WHERE SIZE(e.roleAssignments) >:count")
    List<EmployeeEntity> findEmployeesWithMultipleRoles(@Param("count") int count);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.createdAt >= :startDateTime AND e.createdAt <= :endDateTime")
    List<EmployeeEntity> findEmployeesCreatedBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT DISTINCT e.jobTitle FROM EmployeeEntity e")
    List<String> findAllUniqueJobTitles();

    @Query("SELECT e FROM EmployeeEntity e WHERE e.department.departmentId = :departmentId AND e.role = :role")
    List<EmployeeEntity> findByDepartmentAndRole(@Param("departmentId") int departmentId, @Param("role") EmployeeRole role);

    @Query("SELECT COUNT(e) FROM EmployeeEntity e WHERE FUNCTION('YEAR', e.hireDate) = :year")
    long countEmployeesHiredInYear(@Param("year") int year);

    @Query(value = "SELECT * FROM employees e WHERE e.department_id = :departmentId ORDER BY e.salary DESC LIMIT :limit", nativeQuery = true)
    List<EmployeeEntity> findTopEarnersByDepartment(@Param("departmentId") int departmentId, @Param("limit") int limit);

    @Modifying
    @Query("DELETE FROM EmployeeEntity e WHERE e.isActive = false AND e.updatedAt < :date")
    int deleteInactiveEmployeesBeforeDate(@Param("date") LocalDateTime date);
}

