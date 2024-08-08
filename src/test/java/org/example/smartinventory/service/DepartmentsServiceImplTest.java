package org.example.smartinventory.service;

import org.example.smartinventory.entities.DepartmentEntity;
import org.example.smartinventory.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyIterable;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
class DepartmentsServiceImplTest {


    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentsServiceImpl departmentsService;

    private DepartmentEntity testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = new DepartmentEntity();
        testDepartment.setDepartmentId(1);
        testDepartment.setDepartmentName("Test Department");
        testDepartment.setDepartmentDescription("Test Description");
        testDepartment.setManagerId(1);
        testDepartment.setActive(true);
        testDepartment.setAddress("Test Address");
        testDepartment.setCreatedAt(LocalDateTime.now());
        testDepartment.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testFindAll() {
        when(departmentRepository.findAll()).thenReturn(List.of(testDepartment));
        Collection<DepartmentEntity> result = departmentsService.findAll();
        assertThat(result).hasSize(1).contains(testDepartment);
    }

    @Test
    void testSave() {
        departmentsService.save(testDepartment);
        verify(departmentRepository).save(testDepartment);
    }

    @Test
    void testDelete() {
        departmentsService.delete(testDepartment);
        verify(departmentRepository).delete(testDepartment);
    }

    @Test
    void testFindById() {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(testDepartment));
        Optional<DepartmentEntity> result = departmentsService.findById(1L);
        assertThat(result).isPresent().contains(testDepartment);
    }

    @Test
    void testFindAllById() {
        when(departmentRepository.findAllById(List.of(1))).thenReturn(List.of(testDepartment));
        Collection<DepartmentEntity> result = departmentsService.findAllById(List.of(1L));
        assertThat(result).hasSize(1).contains(testDepartment);
    }

    @Test
    void testFindByDepartmentName() {
        when(departmentRepository.findByDepartmentName("Test Department")).thenReturn(Optional.of(testDepartment));
        Optional<DepartmentEntity> result = departmentsService.findByDepartmentName("Test Department");
        assertThat(result).isPresent().contains(testDepartment);
    }

    @Test
    void testFindAllActiveDepartments() {
        when(departmentRepository.findAllActiveDepartments()).thenReturn(List.of(testDepartment));
        List<DepartmentEntity> result = departmentsService.findAllActiveDepartments();
        assertThat(result).hasSize(1).contains(testDepartment);
    }

    @Test
    void testFindByManagerId() {
        when(departmentRepository.findByManagerId(1)).thenReturn(List.of(testDepartment));
        List<DepartmentEntity> result = departmentsService.findByManagerId(1);
        assertThat(result).hasSize(1).contains(testDepartment);
    }

    @Test
    void testFindDepartmentsCreatedAfter() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        when(departmentRepository.findDepartmentsCreatedAfter(date)).thenReturn(List.of(testDepartment));
        List<DepartmentEntity> result = departmentsService.findDepartmentsCreatedAfter(date);
        assertThat(result).hasSize(1).contains(testDepartment);
    }

    @Test
    void testSearchDepartments() {
        when(departmentRepository.searchDepartments("Test")).thenReturn(List.of(testDepartment));
        List<DepartmentEntity> result = departmentsService.searchDepartments("Test");
        assertThat(result).hasSize(1).contains(testDepartment);
    }

    @Test
    void testCountEmployeesInDepartment() {
        when(departmentRepository.countEmployeesInDepartment(1)).thenReturn(5L);
        long result = departmentsService.countEmployeesInDepartment(1);
        assertThat(result).isEqualTo(5L);
    }

    @Test
    void testUpdateDepartmentStatus() {
        when(departmentRepository.updateDepartmentStatus(1, false)).thenReturn(1);
        boolean result = departmentsService.updateDepartmentStatus(1, false);
        assertThat(result).isTrue();
    }

    @Test
    void testUpdateDepartmentManager() {
        when(departmentRepository.updateDepartmentManager(1, 2)).thenReturn(1);
        boolean result = departmentsService.updateDepartmentManager(1, 2);
        assertThat(result).isTrue();
    }

    @Test
    void testDeleteInactiveDepartments() {
        when(departmentRepository.deleteInactiveDepartments()).thenReturn(1);
        int result = departmentsService.deleteInactiveDepartments();
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testFindByAddress() {
        when(departmentRepository.findByAddress("Test Address")).thenReturn(List.of(testDepartment));
        List<DepartmentEntity> result = departmentsService.findByAddress("Test Address");
        assertThat(result).hasSize(1).contains(testDepartment);
    }

    @Test
    void testCountActiveAndInactiveDepartments() {
        when(departmentRepository.countActiveAndInactiveDepartments()).thenReturn(List.of(new Object[]{true, 5L}, new Object[]{false, 2L}));
        Map<Boolean, Long> result = departmentsService.countActiveAndInactiveDepartments();
        assertThat(result).hasSize(2).containsEntry(true, 5L).containsEntry(false, 2L);
    }

//    @Test
//    void testFindDepartmentsSortedByEmployeeCount() {
//        when(departmentRepository.findDepartmentsSortedByEmployeeCount()).thenReturn((List<Object[]>) List.of(new Object[]{testDepartment, 5L}));
//        List<DepartmentEntity> result = departmentsService.findDepartmentsSortedByEmployeeCount();
//        assertThat(result).hasSize(1).contains(testDepartment);
//    }

    @Test
    void testCreateDepartment() {
        when(departmentRepository.save(testDepartment)).thenReturn(testDepartment);
        DepartmentEntity result = departmentsService.createDepartment(testDepartment);
        assertThat(result).isEqualTo(testDepartment);
    }

    @Test
    void testUpdateDepartment() {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(testDepartment));
        when(departmentRepository.save(any(DepartmentEntity.class))).thenReturn(testDepartment);
        Optional<DepartmentEntity> result = departmentsService.updateDepartment(1, testDepartment);
        assertThat(result).isPresent().contains(testDepartment);
    }

    @Test
    void testDeleteDepartment() {
        when(departmentRepository.existsById(1)).thenReturn(true);
        boolean result = departmentsService.deleteDepartment(1);
        assertThat(result).isTrue();
        verify(departmentRepository).deleteById(1);
    }

    @Test
    void testFindAllPaginated() {
        Page<DepartmentEntity> page = new PageImpl<>(List.of(testDepartment));
        when(departmentRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<DepartmentEntity> result = departmentsService.findAllPaginated(PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1).contains(testDepartment);
    }
}