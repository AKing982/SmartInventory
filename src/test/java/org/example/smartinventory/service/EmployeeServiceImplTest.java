package org.example.smartinventory.service;

import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.model.EmployeeRole;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeEntity testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = new EmployeeEntity();
        testEmployee.setEmployeeId(1);
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setUsername("johndoe");
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setRole(EmployeeRole.INVENTORY_MANAGER);
        testEmployee.setJobTitle("Inventory Clerk");
        testEmployee.setActive(true);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAll() {
        when(employeeRepository.findAll()).thenReturn(List.of(testEmployee));
        Collection<EmployeeEntity> result = employeeService.findAll();
        assertThat(result).hasSize(1).contains(testEmployee);
    }

    @Test
    void testSave() {
        employeeService.save(testEmployee);
        verify(employeeRepository).save(testEmployee);
    }

    @Test
    void testDelete() {
        employeeService.delete(testEmployee);
        verify(employeeRepository).delete(testEmployee);
    }

    @Test
    void testFindById() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        Optional<EmployeeEntity> result = employeeService.findById(1L);
        assertThat(result).isPresent().contains(testEmployee);
    }

    @Test
    void testFindAllById() {
        when(employeeRepository.findAllById(List.of(1))).thenReturn(List.of(testEmployee));
        Collection<EmployeeEntity> result = employeeService.findAllById(List.of(1L));
        assertThat(result).hasSize(1).contains(testEmployee);
    }

    @Test
    void testFindByWarehouseId() {
        when(employeeRepository.findByWarehouseId(1)).thenReturn(List.of(testEmployee));
        List<EmployeeEntity> result = employeeService.findByWarehouseId(1);
        assertThat(result).hasSize(1).contains(testEmployee);
    }

    @Test
    void testFindByJobTitle() {
        when(employeeRepository.findByJobTitle("Inventory Clerk")).thenReturn(List.of(testEmployee));
        List<EmployeeEntity> result = employeeService.findByJobTitle("Inventory Clerk");
        assertThat(result).hasSize(1).contains(testEmployee);
    }

    @Test
    void testFindByRole() {
        when(employeeRepository.findByRole(EmployeeRole.INVENTORY_MANAGER)).thenReturn(List.of(testEmployee));
        List<EmployeeEntity> result = employeeService.findByRole(EmployeeRole.INVENTORY_MANAGER);
        assertThat(result).hasSize(1).contains(testEmployee);
    }

    @Test
    void testFindByActiveStatus() {
        when(employeeRepository.findByActiveStatus(true)).thenReturn(List.of(testEmployee));
        List<EmployeeEntity> result = employeeService.findByActiveStatus(true);
        assertThat(result).hasSize(1).contains(testEmployee);
    }

    @Test
    void testFindByUsername() {
        when(employeeRepository.findByUsername("johndoe")).thenReturn(Optional.of(testEmployee));
        Optional<EmployeeEntity> result = employeeService.findByUsername("johndoe");
        assertThat(result).isPresent().contains(testEmployee);
    }

    @Test
    void testSearchEmployees() {
        when(employeeRepository.searchEmployees("John")).thenReturn(List.of(testEmployee));
        List<EmployeeEntity> result = employeeService.searchEmployees("John");
        assertThat(result).hasSize(1).contains(testEmployee);
    }

    @Test
    void testUpdateEmployeeActiveStatus() {
        // Arrange
        int employeeId = 1;
        boolean isActive = false;
        when(employeeRepository.updateEmployeeActiveStatus(employeeId, isActive)).thenReturn(1);

        // Act
        int result = employeeService.updateEmployeeActiveStatus(employeeId, isActive);

        // Assert
        assertThat(result).isEqualTo(1);
        verify(employeeRepository).updateEmployeeActiveStatus(employeeId, isActive);
    }


    @Test
    void testCreateEmployee() {

        employeeService.save(testEmployee);

        verify(employeeRepository).save(testEmployee);
    }

    @Test
    void testFindPaginated() {
        Page<EmployeeEntity> page = new PageImpl<>(List.of(testEmployee));
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<EmployeeEntity> result = employeeService.findPaginated(0, 10);
        assertThat(result.getContent()).hasSize(1).contains(testEmployee);
    }

    @Test
    void testIsUsernameUnique() {
        when(employeeRepository.findByUsername("johndoe")).thenReturn(Optional.empty());
        boolean result = employeeService.isUsernameUnique("johndoe");
        assertThat(result).isTrue();
    }


}