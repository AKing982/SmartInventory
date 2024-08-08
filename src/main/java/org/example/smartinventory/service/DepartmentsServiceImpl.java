package org.example.smartinventory.service;

import org.example.smartinventory.entities.DepartmentEntity;
import org.example.smartinventory.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartmentsServiceImpl implements DepartmentsService
{
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentsServiceImpl(DepartmentRepository departmentRepository)
    {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Collection<DepartmentEntity> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public void save(DepartmentEntity departmentEntity) {
        departmentRepository.save(departmentEntity);
    }

    @Override
    public void delete(DepartmentEntity departmentEntity) {
        departmentRepository.delete(departmentEntity);
    }

    @Override
    public Optional<DepartmentEntity> findById(Long id) {
        return departmentRepository.findById(Math.toIntExact(id));
    }

    @Override
    public Optional<DepartmentEntity> findById(Integer id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Collection<DepartmentEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }

    @Override
    public Optional<DepartmentEntity> findByDepartmentName(String name) {
        return departmentRepository.findByDepartmentName(name);
    }

    @Override
    public List<DepartmentEntity> findAllActiveDepartments() {
        return departmentRepository.findAllActiveDepartments();
    }

    @Override
    public List<DepartmentEntity> findByManagerId(int managerId) {
        return departmentRepository.findByManagerId(managerId);
    }

    @Override
    public List<DepartmentEntity> findDepartmentsCreatedAfter(LocalDateTime date) {
        return departmentRepository.findDepartmentsCreatedAfter(date);
    }

    @Override
    public List<DepartmentEntity> findDepartmentsUpdatedAfter(LocalDateTime date) {
        return departmentRepository.findDepartmentsUpdatedAfter(date);
    }

    @Override
    public List<DepartmentEntity> searchDepartments(String keyword) {
        return departmentRepository.searchDepartments(keyword);
    }

    @Override
    public long countEmployeesInDepartment(int departmentId) {
        return departmentRepository.countEmployeesInDepartment(departmentId);
    }

    @Override
    public List<DepartmentEntity> findDepartmentsWithMoreThanXEmployees(int count) {
        return departmentRepository.findDepartmentsWithMoreThanXEmployees(count);
    }

    @Override
    public List<DepartmentEntity> findDepartmentsWithNoEmployees() {
        return departmentRepository.findDepartmentsWithNoEmployees();
    }

    @Override
    public int updateDepartmentStatus(int id, boolean status) {
        return departmentRepository.updateDepartmentStatus(id, status);
    }

    @Override
    public int updateDepartmentManager(int id, int managerId) {
        return departmentRepository.updateDepartmentManager(id, managerId);
    }

    @Override
    public int deleteInactiveDepartments() {
        return departmentRepository.deleteInactiveDepartments();
    }

    @Override
    public List<DepartmentEntity> findByAddress(String address) {
        return departmentRepository.findByAddress(address);
    }

    @Override
    public Map<Boolean, Long> countActiveAndInactiveDepartments() {
        return Map.of();
    }

    @Override
    public List<DepartmentEntity> findDepartmentsSortedByEmployeeCount() {
        return List.of();
    }

    @Override
    public List<DepartmentEntity> findDepartmentsCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return departmentRepository.findDepartmentsCreatedBetween(startDate, endDate);
    }

    @Override
    public List<DepartmentEntity> findUpdatedDepartments() {
        return departmentRepository.findUpdatedDepartments();
    }

    @Override
    public List<DepartmentEntity> findDepartmentsByCustomCriteria(String name, Boolean isActive, Integer managerId) {
        return departmentRepository.findDepartmentsByCustomCriteria(name, isActive, managerId);
    }

    @Override
    public int bulkUpdateDepartmentAddresses(String oldAddress, String newAddress) {
        return departmentRepository.bulkUpdateDepartmentAddresses(oldAddress, newAddress);
    }

    @Override
    public DepartmentEntity createDepartment(DepartmentEntity department) {
        return null;
    }

    @Override
    public int updateDepartment(int id, DepartmentEntity updatedDepartment) {
        return departmentRepository.updateDepartment(id, updatedDepartment);
    }

    @Override
    public int deleteDepartment(int id) {
        return departmentRepository.deleteDepartmentById(id);
    }

    @Override
    public Page<DepartmentEntity> findAllPaginated(Pageable pageable) {
        return null;
    }


    @Override
    public boolean isDepartmentNameUnique(String name) {
        return false;
    }


    @Override
    public Optional<DepartmentEntity> assignEmployeeToDepartment(int employeeId, int departmentId) {
        return Optional.empty();
    }

    @Override
    public Optional<DepartmentEntity> removeEmployeeFromDepartment(int employeeId, int departmentId) {
        return Optional.empty();
    }

    @Override
    public Optional<BigDecimal> getTotalBudgetForAllDepartments() {
        return Optional.empty();
    }
}
