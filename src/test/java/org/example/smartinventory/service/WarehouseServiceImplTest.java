package org.example.smartinventory.service;

import org.example.smartinventory.entities.WarehouseEntity;
import org.example.smartinventory.model.WarehouseType;
import org.example.smartinventory.repository.WarehouseRepository;
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyIterable;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceImplTest {

    @InjectMocks
    private WarehouseServiceImpl service;

    @Mock
    private WarehouseRepository repository;

    private WarehouseEntity testWarehouse;

    @BeforeEach
    void setUp() {
        testWarehouse = new WarehouseEntity();
        testWarehouse.setWarehouseId(1);
        testWarehouse.setWarehouseName("Test Warehouse");
        testWarehouse.setWarehouseAddress("123 Test St");
        testWarehouse.setTotalCapacity(1000.0);
        testWarehouse.setUsedCapacity(500.0);
        testWarehouse.setWarehouseType(WarehouseType.STORAGE);
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(testWarehouse));
        Collection<WarehouseEntity> result = service.findAll();
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testSave() {
        service.save(testWarehouse);
        verify(repository).save(testWarehouse);
    }

    @Test
    void testDelete() {
        service.delete(testWarehouse);
        verify(repository).delete(testWarehouse);
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(testWarehouse));
        Optional<WarehouseEntity> result = service.findById(1L);
        assertThat(result).isPresent().contains(testWarehouse);
    }

    @Test
    void testFindAllById() {
        when(repository.findAllById(List.of(1L))).thenReturn(List.of(testWarehouse));
        Collection<WarehouseEntity> result = service.findAllById(List.of(1L));
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testCreateWarehouse() {
        when(repository.save(any(WarehouseEntity.class))).thenReturn(testWarehouse);
        WarehouseEntity result = service.createWarehouse(testWarehouse);
        assertThat(result).isEqualTo(testWarehouse);
    }

    @Test
    void testGetAllWarehousesPaginated() {
        Page<WarehouseEntity> page = new PageImpl<>(List.of(testWarehouse));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        Page<WarehouseEntity> result = service.getAllWarehousesPaginated(PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testUpdateWarehouse() {
        when(repository.save(any(WarehouseEntity.class))).thenReturn(testWarehouse);
        WarehouseEntity result = service.updateWarehouse(testWarehouse);
        assertThat(result).isEqualTo(testWarehouse);
    }

    @Test
    void testDeleteWarehouse() {
        service.deleteWarehouse(1);
        verify(repository).deleteById(1L);
    }

    @Test
    void testSearchWarehouses() {
        when(repository.searchWarehouses("Test")).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.searchWarehouses("Test");
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetWarehousesByType() {
        when(repository.getWarehousesByType(WarehouseType.STORAGE)).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.getWarehousesByType(WarehouseType.STORAGE);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetWarehousesByCapacityRange() {
        when(repository.getWarehousesByCapacityRange(500.0, 1500.0)).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.getWarehousesByCapacityRange(500.0, 1500.0);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetWarehousesByUtilizationRate() {
        when(repository.getWarehousesByUtilizationRate(0.4, 0.6)).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.getWarehousesByUtilizationRate(0.4, 0.6);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetTotalCapacity() {
        when(repository.getTotalCapacity(1)).thenReturn(1000.0);
        double result = service.getTotalCapacity(1);
        assertThat(result).isEqualTo(1000.0);
    }

    @Test
    void testGetUsedCapacity() {
        when(repository.getUsedCapacity(1)).thenReturn(500.0);
        double result = service.getUsedCapacity(1);
        assertThat(result).isEqualTo(500.0);
    }

    @Test
    void testGetAvailableCapacity() {
        when(repository.getAvailableCapacity(1)).thenReturn(500.0);
        double result = service.getAvailableCapacity(1);
        assertThat(result).isEqualTo(500.0);
    }

    @Test
    void testUpdateCapacity() {
        when(repository.findById(1L)).thenReturn(Optional.of(testWarehouse));
        when(repository.save(any(WarehouseEntity.class))).thenReturn(testWarehouse);
        WarehouseEntity result = service.updateCapacity(1, 1500.0);
        assertThat(result.getTotalCapacity()).isEqualTo(1500.0);
    }

    @Test
    void testUpdateUsedCapacity() {
        when(repository.findById(1L)).thenReturn(Optional.of(testWarehouse));
        when(repository.save(any(WarehouseEntity.class))).thenReturn(testWarehouse);
        WarehouseEntity result = service.updateUsedCapacity(1, 750.0);
        assertThat(result.getUsedCapacity()).isEqualTo(750.0);
    }

    @Test
    void testGetWarehouseManager() {
        when(repository.getWarehouseManager(1)).thenReturn("John Doe");
        String result = service.getWarehouseManager(1);
        assertThat(result).isEqualTo("John Doe");
    }

    @Test
    void testGetMostUtilizedWarehouses() {
        when(repository.getMostUtilizedWarehouses(any(Pageable.class))).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.getMostUtilizedWarehouses(5);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetLeastUtilizedWarehouses() {
        when(repository.getLeastUtilizedWarehouses(any(Pageable.class))).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.getLeastUtilizedWarehouses(5);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetWarehousesEstablishedBefore() {
        LocalDate date = LocalDate.now();
        when(repository.getWarehousesEstablishedBefore(date)).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.getWarehousesEstablishedBefore(date);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetWarehousesEstablishedAfter() {
        LocalDate date = LocalDate.now().minusYears(1);
        when(repository.getWarehousesEstablishedAfter(date)).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.getWarehousesEstablishedAfter(date);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testGetWarehouseAge() {
        when(repository.getWarehouseAge(1)).thenReturn(365);
        int result = service.getWarehouseAge(1);
        assertThat(result).isEqualTo(365);
    }

    @Test
    void testBulkCreateWarehouses() {
        List<WarehouseEntity> warehouses = List.of(testWarehouse);
        when(repository.saveAll(warehouses)).thenReturn(warehouses);
        List<WarehouseEntity> result = service.bulkCreateWarehouses(warehouses);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testBulkDeleteWarehouses() {
        List<Integer> ids = List.of(1);
        service.bulkDeleteWarehouses(ids);
        verify(repository).deleteAllById(anyIterable());
    }

    @Test
    void testBulkUpdateWarehouseType() {
        List<Integer> ids = List.of(1);
        when(repository.findAllById(anyIterable())).thenReturn(List.of(testWarehouse));
        when(repository.saveAll(anyIterable())).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.bulkUpdateWarehouseType(ids, WarehouseType.COLD_STORAGE);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getWarehouseType()).isEqualTo(WarehouseType.COLD_STORAGE);
    }

    @Test
    void testGetWarehouseCount() {
        when(repository.getWarehouseCount()).thenReturn(5L);
        long result = service.getWarehouseCount();
        assertThat(result).isEqualTo(5L);
    }

    @Test
    void testGetWarehouseCountByType() {
        when(repository.getWarehouseCountByType(WarehouseType.STORAGE)).thenReturn(3L);
        long result = service.getWarehouseCountByType(WarehouseType.STORAGE);
        assertThat(result).isEqualTo(3L);
    }

    @Test
    void testGetAverageWarehouseCapacity() {
        when(repository.getAverageWarehouseCapacity()).thenReturn(1000.0);
        double result = service.getAverageWarehouseCapacity();
        assertThat(result).isEqualTo(1000.0);
    }

    @Test
    void testFindWarehousesWithAvailableCapacity() {
        when(repository.findWarehousesWithAvailableCapacity(500.0)).thenReturn(List.of(testWarehouse));
        List<WarehouseEntity> result = service.findWarehousesWithAvailableCapacity(500.0);
        assertThat(result).hasSize(1).contains(testWarehouse);
    }

    @Test
    void testIsUniqueWarehouseName() {
        when(repository.isUniqueWarehouseName("Test Warehouse")).thenReturn(true);
        boolean result = service.isUniqueWarehouseName("Test Warehouse");
        assertThat(result).isTrue();
    }

    @Test
    void testFindWarehousesByCustomCriteria() {
        Page<WarehouseEntity> page = new PageImpl<>(List.of(testWarehouse));
        when(repository.findWarehousesByCustomCriteria(anyString(), any(), any(), any(), any(), any(Pageable.class))).thenReturn(page);
        Page<WarehouseEntity> result = service.findWarehousesByCustomCriteria("Test", WarehouseType.STORAGE, 500.0, 1500.0, LocalDate.now().minusYears(1), PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1).contains(testWarehouse);
    }

    @AfterEach
    void tearDown() {
    }
}