package org.example.smartinventory.repository;

import org.example.smartinventory.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer>
{
    @Query("SELECT d FROM DepartmentEntity d WHERE d.departmentName = :name")
    Optional<DepartmentEntity> findByDepartmentName(@Param("name") String name);

    // Find all active departments
    @Query("SELECT d FROM DepartmentEntity d WHERE d.isActive = true")
    List<DepartmentEntity> findAllActiveDepartments();

    // Find departments by manager
    @Query("SELECT d FROM DepartmentEntity d WHERE d.managerId = :managerId")
    List<DepartmentEntity> findByManagerId(@Param("managerId") int managerId);

    // Find departments created after a certain date
    @Query("SELECT d FROM DepartmentEntity d WHERE d.createdAt > :date")
    List<DepartmentEntity> findDepartmentsCreatedAfter(@Param("date") LocalDateTime date);

    // Find departments updated after a certain date
    @Query("SELECT d FROM DepartmentEntity d WHERE d.updatedAt > :date")
    List<DepartmentEntity> findDepartmentsUpdatedAfter(@Param("date") LocalDateTime date);

    // Search departments by name or description
    @Query("SELECT d FROM DepartmentEntity d WHERE LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.departmentDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DepartmentEntity> searchDepartments(@Param("keyword") String keyword);

    // Count employees in a department
    @Query("SELECT COUNT(e) FROM DepartmentEntity d JOIN d.employees e WHERE d.departmentId = :departmentId")
    long countEmployeesInDepartment(@Param("departmentId") int departmentId);

    // Find departments with more than X employees
    @Query("SELECT d FROM DepartmentEntity d WHERE SIZE(d.employees) > :count")
    List<DepartmentEntity> findDepartmentsWithMoreThanXEmployees(@Param("count") int count);

    // Find departments with no employees
    @Query("SELECT d FROM DepartmentEntity d WHERE d.employees IS EMPTY")
    List<DepartmentEntity> findDepartmentsWithNoEmployees();

    // Update department status
    @Modifying
    @Query("UPDATE DepartmentEntity d SET d.isActive = :status WHERE d.departmentId = :id")
    int updateDepartmentStatus(@Param("id") int id, @Param("status") boolean status);

    // Update department manager
    @Modifying
    @Query("UPDATE DepartmentEntity d SET d.managerId = :managerId WHERE d.departmentId = :id")
    int updateDepartmentManager(@Param("id") int id, @Param("managerId") int managerId);

    // Delete inactive departments
    @Modifying
    @Query("DELETE FROM DepartmentEntity d WHERE d.isActive = false")
    int deleteInactiveDepartments();

    // Find departments by address
    @Query("SELECT d FROM DepartmentEntity d WHERE d.address LIKE %:address%")
    List<DepartmentEntity> findByAddress(@Param("address") String address);

    // Count active and inactive departments
    @Query("SELECT d.isActive, COUNT(d) FROM DepartmentEntity d GROUP BY d.isActive")
    List<Object[]> countActiveAndInactiveDepartments();

    // Find departments and sort by employee count
    @Query("SELECT d, SIZE(d.employees) as empCount FROM DepartmentEntity d ORDER BY empCount DESC")
    List<Object[]> findDepartmentsSortedByEmployeeCount();

    // Find departments created between two dates
    @Query("SELECT d FROM DepartmentEntity d WHERE d.createdAt BETWEEN :startDate AND :endDate")
    List<DepartmentEntity> findDepartmentsCreatedBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Find departments that have been updated
    @Query("SELECT d FROM DepartmentEntity d WHERE d.updatedAt IS NOT NULL")
    List<DepartmentEntity> findUpdatedDepartments();

    // Custom query with multiple optional parameters
    @Query("SELECT d FROM DepartmentEntity d WHERE " +
            "(:name IS NULL OR d.departmentName LIKE %:name%) AND " +
            "(:isActive IS NULL OR d.isActive = :isActive) AND " +
            "(:managerId IS NULL OR d.managerId = :managerId)")
    List<DepartmentEntity> findDepartmentsByCustomCriteria(
            @Param("name") String name,
            @Param("isActive") Boolean isActive,
            @Param("managerId") Integer managerId);

    // Bulk update department addresses
    @Modifying
    @Query("UPDATE DepartmentEntity d SET d.address = :newAddress WHERE d.address = :oldAddress")
    int bulkUpdateDepartmentAddresses(@Param("oldAddress") String oldAddress, @Param("newAddress") String newAddress);
}
