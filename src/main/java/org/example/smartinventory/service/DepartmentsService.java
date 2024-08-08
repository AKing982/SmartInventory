package org.example.smartinventory.service;

import org.example.smartinventory.entities.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentsService extends ServiceModel<DepartmentEntity>
{

    void save(DepartmentEntity departmentEntity);

    void delete(DepartmentEntity departmentEntity);

    Optional<DepartmentEntity> findById(Integer id);

    Optional<DepartmentEntity> findByDepartmentName(String name);

    List<DepartmentEntity> findAllActiveDepartments();

    List<DepartmentEntity> findByManagerId(int managerId);

    List<DepartmentEntity> findDepartmentsCreatedAfter(LocalDateTime date);

    List<DepartmentEntity> findDepartmentsUpdatedAfter(LocalDateTime date);

    List<DepartmentEntity> searchDepartments(String keyword);

    long countEmployeesInDepartment(int departmentId);

    List<DepartmentEntity> findDepartmentsWithMoreThanXEmployees(int count);

    List<DepartmentEntity> findDepartmentsWithNoEmployees();

    int updateDepartmentStatus(int id, boolean status);

    int updateDepartmentManager(int id, int managerId);

    int deleteInactiveDepartments();

    List<DepartmentEntity> findByAddress(String address);

    Map<Boolean, Long> countActiveAndInactiveDepartments();

    List<DepartmentEntity> findDepartmentsSortedByEmployeeCount();

    List<DepartmentEntity> findDepartmentsCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<DepartmentEntity> findUpdatedDepartments();

    List<DepartmentEntity> findDepartmentsByCustomCriteria(String name, Boolean isActive, Integer managerId);

    int bulkUpdateDepartmentAddresses(String oldAddress, String newAddress);

    DepartmentEntity createDepartment(DepartmentEntity department);

    int updateDepartment(int id, DepartmentEntity updatedDepartment);

    int deleteDepartment(int id);

    Page<DepartmentEntity> findAllPaginated(Pageable pageable);

    boolean isDepartmentNameUnique(String name);

    Optional<DepartmentEntity> assignEmployeeToDepartment(int employeeId, int departmentId);

    Optional<DepartmentEntity> removeEmployeeFromDepartment(int employeeId, int departmentId);

    Optional<BigDecimal> getTotalBudgetForAllDepartments();

}
